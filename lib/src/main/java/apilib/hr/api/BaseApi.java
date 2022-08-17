package apilib.hr.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import apilib.hr.cache.Cache;
import apilib.hr.cache.CacheManager;
import apilib.hr.constant.CacheName;
import apilib.hr.constant.ParameterConstant;
import apilib.hr.dto.inbound.request.RequestBaseDto;
import apilib.hr.dto.inbound.response.ResponseBaseDto;
import apilib.hr.factory.ExecutorFactory;
import apilib.hr.util.HttpUtil;

public abstract class BaseApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public final Logger log = LogManager.getLogger(this.getClass());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//super.doPost(req, resp);
		try {
			log.info("Async support is " + req.isAsyncSupported());
			
			ExecutorService executor = ExecutorFactory.getThreadPoolExecutor();
			final AsyncContext asyncContext = req.startAsync();
			asyncContext.setTimeout(1200000);
			executor.execute(new Runnable() {

				@Override
				public void run() {
					log.info("Sent a request to normal thread");
					
					HttpServletRequest req = null;
					HttpServletResponse res = null;
					PrintWriter responseWriter = null;
					String requestBody = "";
					String responseBody = "";
					
					try {
						req = (HttpServletRequest) asyncContext.getRequest();
						res = (HttpServletResponse)asyncContext.getResponse();
						responseWriter = res.getWriter();
						
						HttpUtil httpUtil = HttpUtil.of(req.getReader());
					} catch(Exception e) {
						
					}
				}
				
			});
		} catch(Exception e) {
			log.error(e);
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doDelete(req, resp);
	}
	
	private RequestBaseDto preProcess(HttpUtil httpUtil, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType(ParameterConstant.JSON_CONTENT_TYPE);
		
		RequestBaseDto requestDto = null;
		String requestUri = req.getRequestURI();
		Cache literalCache = (Cache) CacheManager.getInstance().getCache(CacheName.LITERAL_CLASS_CACHE_NAME);
		Object tClass = literalCache.get(requestUri);
		if (tClass != null) {
			Object tObject = httpUtil.toObject((Class<?>) tClass);
			requestDto = (RequestBaseDto) tObject;
		}
		
		return requestDto;
	}
	
	protected abstract ResponseBaseDto execute(RequestBaseDto requestBaseDto);
}

/*
 * JSP generated by Resin Professional 4.0.48 (built Wed, 17 Feb 2016 11:03:24 PST)
 */

package _jsp._web_22dinf._pages._srm;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import com.lpmas.framework.config.*;
import com.lpmas.framework.bean.*;
import com.lpmas.framework.page.*;
import com.lpmas.framework.util.*;
import com.lpmas.framework.web.*;
import com.lpmas.admin.bean.*;
import com.lpmas.admin.business.*;
import com.lpmas.admin.config.*;
import com.lpmas.srm.config.*;
import com.lpmas.srm.bean.*;

public class _supplieraddressentityselect__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  private boolean _caucho_isNotModified;
  private com.caucho.jsp.PageManager _jsp_pageManager;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    com.caucho.jsp.PageContextImpl pageContext = _jsp_pageManager.allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);

    TagState _jsp_state = null;

    try {
      _jspService(request, response, pageContext, _jsp_application, session, _jsp_state);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_pageManager.freePageContext(pageContext);
    }
  }
  
  private void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response,
              com.caucho.jsp.PageContextImpl pageContext,
              javax.servlet.ServletContext application,
              javax.servlet.http.HttpSession session,
              TagState _jsp_state)
    throws Throwable
  {
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    javax.servlet.jsp.tagext.JspTag _jsp_parent_tag = null;
    com.caucho.jsp.PageContextImpl _jsp_parentContext = pageContext;
    response.setContentType("text/html; charset=UTF-8");

    out.write(_jsp_string0, 0, _jsp_string0.length);
    
	String callbackFun = ParamKit.getParameter(request, "callbackFun", "callbackFun");
	AdminUserHelper adminHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<SupplierAddressEntityBean> list = (List<SupplierAddressEntityBean>)request.getAttribute("SupplierAddressEntityList");
	int supplierAddressId = ParamKit.getIntParameter(request, "addressId", 0);
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");

    out.write(_jsp_string1, 0, _jsp_string1.length);
    String STATIC_URL = "http://dev.portal.lpmas-admin.net:9010"; 
    out.write(_jsp_string2, 0, _jsp_string2.length);
    String REGION_URL = "http://test.region.lpmas-admin.net:9030"; 
    out.write(_jsp_string2, 0, _jsp_string2.length);
    String DOMAIN = "lpmas-admin.net"; 
    out.write(_jsp_string3, 0, _jsp_string3.length);
    out.print((STATIC_URL ));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((STATIC_URL ));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    out.print((STATIC_URL ));
    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((STATIC_URL ));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    out.print((DOMAIN));
    out.write(_jsp_string8, 0, _jsp_string8.length);
    out.print((ParamKit.getParameter(request, "supplierName", "") ));
    out.write(_jsp_string9, 0, _jsp_string9.length);
    
    for(SupplierAddressEntityBean bean:list){
    out.write(_jsp_string10, 0, _jsp_string10.length);
    out.print((bean.getAddressId()));
    out.write(_jsp_string11, 0, _jsp_string11.length);
    out.print((supplierAddressId==bean.getAddressId() ? "checked" : ""));
    out.write(_jsp_string12, 0, _jsp_string12.length);
    out.print((bean.getAddressId()));
    out.write(_jsp_string13, 0, _jsp_string13.length);
    out.print((bean.getSupplierTypeName()));
    out.write(_jsp_string14, 0, _jsp_string14.length);
    out.print((bean.getAddressId()));
    out.write(_jsp_string13, 0, _jsp_string13.length);
    out.print((bean.getSupplierName()));
    out.write(_jsp_string15, 0, _jsp_string15.length);
    out.print((bean.getAddressId()));
    out.write(_jsp_string13, 0, _jsp_string13.length);
    out.print((bean.getSupplierAddress()));
    out.write(_jsp_string16, 0, _jsp_string16.length);
    out.print((bean.getAddressId()));
    out.write(_jsp_string17, 0, _jsp_string17.length);
    out.print((JsonKit.toJson(bean)));
    out.write(_jsp_string18, 0, _jsp_string18.length);
    } 
    out.write(_jsp_string19, 0, _jsp_string19.length);
    for(String[] condArray :COND_LIST){ 
    out.write(_jsp_string20, 0, _jsp_string20.length);
    out.print((condArray[0] ));
    out.write(_jsp_string21, 0, _jsp_string21.length);
    out.print((condArray[0] ));
    out.write(_jsp_string22, 0, _jsp_string22.length);
    out.print((condArray[1] ));
    out.write(_jsp_string23, 0, _jsp_string23.length);
    } 
    out.write(_jsp_string24, 0, _jsp_string24.length);
    if(PAGE_BEAN.getCurrentPageNumber()>1){ 
    out.write(_jsp_string25, 0, _jsp_string25.length);
    out.print((PAGE_BEAN.getPrePageNumber() ));
    out.write(_jsp_string26, 0, _jsp_string26.length);
    } 
    out.write(_jsp_string27, 0, _jsp_string27.length);
    out.print((PAGE_BEAN.getTotalRecordNumber() ));
    out.write(_jsp_string28, 0, _jsp_string28.length);
    out.print((PAGE_BEAN.getPageSize() ));
    out.write(_jsp_string29, 0, _jsp_string29.length);
    out.print((PAGE_BEAN.getCurrentPageNumber() ));
    out.write(_jsp_string30, 0, _jsp_string30.length);
    out.print((PAGE_BEAN.getTotalPageNumber() ));
    out.write(_jsp_string31, 0, _jsp_string31.length);
    if(PAGE_BEAN.getCurrentPageNumber() < PAGE_BEAN.getTotalPageNumber()){ 
    out.write(_jsp_string32, 0, _jsp_string32.length);
    out.print((PAGE_BEAN.getNextPageNumber() ));
    out.write(_jsp_string33, 0, _jsp_string33.length);
    out.print((PAGE_BEAN.getTotalPageNumber() ));
    out.write(_jsp_string34, 0, _jsp_string34.length);
    } 
    out.write(_jsp_string35, 0, _jsp_string35.length);
    out.print((PAGE_BEAN.getTotalPageNumber() ));
    out.write(_jsp_string36, 0, _jsp_string36.length);
    out.print((ParamKit.getParameter(request, "echoParameter", "")));
    out.write(_jsp_string37, 0, _jsp_string37.length);
    out.print((callbackFun ));
    out.write(_jsp_string38, 0, _jsp_string38.length);
  }

  private com.caucho.make.DependencyContainer _caucho_depends
    = new com.caucho.make.DependencyContainer();

  public java.util.ArrayList<com.caucho.vfs.Dependency> _caucho_getDependList()
  {
    return _caucho_depends.getDependencies();
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    _caucho_depends.add(depend);
  }

  protected void _caucho_setNeverModified(boolean isNotModified)
  {
    _caucho_isNotModified = true;
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;

    if (_caucho_isNotModified)
      return false;

    if (com.caucho.server.util.CauchoSystem.getVersionId() != -8002497470487589159L)
      return true;

    return _caucho_depends.isModified();
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
    TagState tagState;
  }

  public void init(com.caucho.vfs.Path appDir)
    throws javax.servlet.ServletException
  {
    com.caucho.vfs.Path resinHome = com.caucho.server.util.CauchoSystem.getResinHome();
    com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
    mergePath.addMergePath(appDir);
    mergePath.addMergePath(resinHome);
    com.caucho.loader.DynamicClassLoader loader;
    loader = (com.caucho.loader.DynamicClassLoader) getClass().getClassLoader();
    String resourcePath = loader.getResourcePathSpecificFirst();
    mergePath.addClassPath(resourcePath);
    com.caucho.vfs.Depend depend;
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/pages/srm/SupplierAddressEntitySelect.jsp"), -5590021757016238329L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/pages/include/header.jsp"), 2483963632988091070L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/pages/include/page.jsp"), -7400274417948715423L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
  }

  final static class TagState {

    void release()
    {
    }
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void caucho_init(ServletConfig config)
  {
    try {
      com.caucho.server.webapp.WebApp webApp
        = (com.caucho.server.webapp.WebApp) config.getServletContext();
      init(config);
      if (com.caucho.jsp.JspManager.getCheckInterval() >= 0)
        _caucho_depends.setCheckInterval(com.caucho.jsp.JspManager.getCheckInterval());
      _jsp_pageManager = webApp.getJspApplicationContext().getPageManager();
      com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
      com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.InitPageContextImpl(webApp, this);
    } catch (Exception e) {
      throw com.caucho.config.ConfigException.create(e);
    }
  }

  private final static char []_jsp_string12;
  private final static char []_jsp_string34;
  private final static char []_jsp_string10;
  private final static char []_jsp_string18;
  private final static char []_jsp_string14;
  private final static char []_jsp_string28;
  private final static char []_jsp_string38;
  private final static char []_jsp_string11;
  private final static char []_jsp_string25;
  private final static char []_jsp_string13;
  private final static char []_jsp_string7;
  private final static char []_jsp_string19;
  private final static char []_jsp_string33;
  private final static char []_jsp_string29;
  private final static char []_jsp_string4;
  private final static char []_jsp_string22;
  private final static char []_jsp_string37;
  private final static char []_jsp_string9;
  private final static char []_jsp_string17;
  private final static char []_jsp_string24;
  private final static char []_jsp_string6;
  private final static char []_jsp_string20;
  private final static char []_jsp_string0;
  private final static char []_jsp_string16;
  private final static char []_jsp_string1;
  private final static char []_jsp_string35;
  private final static char []_jsp_string21;
  private final static char []_jsp_string31;
  private final static char []_jsp_string3;
  private final static char []_jsp_string30;
  private final static char []_jsp_string15;
  private final static char []_jsp_string8;
  private final static char []_jsp_string32;
  private final static char []_jsp_string23;
  private final static char []_jsp_string2;
  private final static char []_jsp_string26;
  private final static char []_jsp_string27;
  private final static char []_jsp_string36;
  private final static char []_jsp_string5;
  static {
    _jsp_string12 = "></td>\r\n      <td id=\"supplierType_".toCharArray();
    _jsp_string34 = ");\">&nbsp;</em>".toCharArray();
    _jsp_string10 = " \r\n    <tr>\r\n      <td align=\"center\"><input type=\"radio\" name=\"supplierAddressId\" value=\"".toCharArray();
    _jsp_string18 = "'></td>\r\n    </tr>	\r\n    ".toCharArray();
    _jsp_string14 = "</td>\r\n      <td id=\"supplierName_".toCharArray();
    _jsp_string28 = "</b>\u6761  \u6bcf\u9875\u663e\u793a<b>".toCharArray();
    _jsp_string38 = "(supplierAddressJsonStr,echoParameter);\r\n	try{ self.parent.jQuery.fancybox.close(); }catch(e){console.log(e);}\r\n}\r\n\r\n\r\n</script>\r\n</html>".toCharArray();
    _jsp_string11 = "\" ".toCharArray();
    _jsp_string25 = "<em class=\"first\" onclick=\"javascript:goPage('formPage',1);\">&nbsp;</em>\r\n  <em class=\"pre\" onclick=\"javascript:goPage('formPage',".toCharArray();
    _jsp_string13 = "\">".toCharArray();
    _jsp_string7 = "/js/ui.js\"></script>\r\n<script type=\"text/javascript\">\r\n       document.domain='".toCharArray();
    _jsp_string19 = "\r\n  </table>\r\n</form>\r\n<ul class=\"page_info\">\r\n\r\n<form name=\"formPage\" method=\"post\" action=\"\">\r\n<input type=\"hidden\" name=\"pageNum\" id=\"pageNum\" value=\"\"/>\r\n".toCharArray();
    _jsp_string33 = ");\">&nbsp;</em>\r\n  <em class=\"last\" onclick=\"javascript:goPage('formPage',".toCharArray();
    _jsp_string29 = "</b>\u6761 \u5f53\u524d\u7b2c<b>".toCharArray();
    _jsp_string4 = "/css/main.css\" type=\"text/css\" rel=\"stylesheet\" />\r\n<script type='text/javascript' src=\"".toCharArray();
    _jsp_string22 = "\" value=\"".toCharArray();
    _jsp_string37 = "';\r\n	self.parent.".toCharArray();
    _jsp_string9 = "\" size=\"20\"/>\r\n    <input name=\"\" type=\"submit\" class=\"search_btn_sub\" value=\"\u67e5\u8be2\"/>\r\n  </div>\r\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" class=\"table_style\">\r\n    <tr>\r\n      <th>\u9009\u62e9</th>\r\n      <th>\u4f9b\u5e94\u5546\u7c7b\u578b</th>\r\n      <th>\u4f9b\u5e94\u5546</th>\r\n      <th>\u4f9b\u5e94\u5546\u5730\u5740</th>\r\n    </tr>\r\n    ".toCharArray();
    _jsp_string17 = "\" value='".toCharArray();
    _jsp_string24 = "\r\n</form>\r\n<li class=\"page_num\">\r\n  ".toCharArray();
    _jsp_string6 = "/js/common.js\"></script>\r\n<script type='text/javascript' src=\"".toCharArray();
    _jsp_string20 = "\r\n<input type=\"hidden\" name=\"".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string16 = "</td>\r\n      <td><input type=\"hidden\" id=\"supplierAddressJsonStr_".toCharArray();
    _jsp_string1 = "\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string35 = "\r\n  <input name=\"goPageNum\" id=\"goPageNum\" type=\"text\" class=\"page_int_txt\"/><input name=\"\" type=\"button\" class=\"page_btn_go\" value=\"GO\" onclick=\"javascript:goInputPage('formPage',".toCharArray();
    _jsp_string21 = "\" id=\"".toCharArray();
    _jsp_string31 = "\u9875 \r\n  ".toCharArray();
    _jsp_string3 = "\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n<title>\u4f9b\u5e94\u5546\u5730\u5740</title>\r\n<link href=\"".toCharArray();
    _jsp_string30 = "</b>/".toCharArray();
    _jsp_string15 = "</td>\r\n      <td id=\"supplierCompleteAddress_".toCharArray();
    _jsp_string8 = "'; \r\n</script>\r\n</head>\r\n<body class=\"article_bg\">\r\n<div class=\"article_tit\">\r\n	<p class=\"article_tit\">\u4f9b\u5e94\u5546\u5730\u5740</p>\r\n</div>\r\n<form name=\"formSearch\" method=\"post\" action=\"SupplierAddressEntitySelect.do\">\r\n  <div class=\"search_form\">\r\n    <em class=\"em1\">\u4f9b\u5e94\u5546\u540d\u79f0\uff1a</em>\r\n    <input type=\"text\" name=\"supplierName\" id=\"supplierName\" value=\"".toCharArray();
    _jsp_string32 = "<em class=\"next\" onclick=\"javascript:goPage('formPage',".toCharArray();
    _jsp_string23 = "\"/>".toCharArray();
    _jsp_string2 = "\r\n".toCharArray();
    _jsp_string26 = ");\"> &nbsp;</em>".toCharArray();
    _jsp_string27 = "\r\n       \u603b\u8bb0\u5f55<b>".toCharArray();
    _jsp_string36 = ");\"/> \r\n</li>\r\n<li class=\"page_left_btn\">\r\n  <input type=\"submit\" name=\"button\" id=\"button\" class=\"modifysubbtn\" value=\"\u9009\u62e9\" onclick=\"callbackTo()\" />\r\n</li>\r\n</ul>\r\n</body>\r\n<script>\r\nfunction callbackTo(){\r\n	var addressId = $('input:radio[name=supplierAddressId]:checked').val();\r\n	if (typeof(addressId) == 'undefined'){\r\n		alert(\"\u8bf7\u9009\u62e9\u4f9b\u5e94\u5546\u5730\u5740\");\r\n		return;\r\n	}\r\n	var supplierAddressJsonStr = $(\"#supplierAddressJsonStr_\"+addressId).val();\r\n	var echoParameter = '".toCharArray();
    _jsp_string5 = "/js/jquery.js\"></script>\r\n<script type='text/javascript' src=\"".toCharArray();
  }
}

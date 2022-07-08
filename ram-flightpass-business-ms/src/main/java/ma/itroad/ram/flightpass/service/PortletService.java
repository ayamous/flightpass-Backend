

package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.PortletBean;
import ma.itroad.ram.flightpass.model.enums.PageName;
import org.springframework.web.multipart.MultipartFile;
import java.lang.Long;
import java.util.List;

public interface PortletService {
    PortletBean postPortlet(PortletBean portletBean , MultipartFile file);
    List<PortletBean> getPortlets();
    List<PortletBean>getPortletsBySegmentId(Long segmentId);
    List<PortletBean>getPortletsByPageName(PageName pageName);
    List<PortletBean> getPortletsBySegmentIdAndPageName(Long segmentId, PageName pageName);
    PortletBean getPortletById(Long portletId);
    void deletePortletById(Long portletId );
    PortletBean patchPortletById(Long id, PortletBean portletBean ,MultipartFile file);



}



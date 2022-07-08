

package ma.itroad.ram.flightpass.repository;


import ma.itroad.ram.flightpass.model.entity.Portlet;
import ma.itroad.ram.flightpass.model.enums.PageName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PortletRepository extends JpaRepository<Portlet, Long> {
    List<Portlet> findBySegmentRef_Id(Long segmentId);
    List<Portlet> findBySegmentRef_IdAndPageName(Long segmentRef_id, PageName pageName);
    List<Portlet> findByPageName(PageName pageName);
}



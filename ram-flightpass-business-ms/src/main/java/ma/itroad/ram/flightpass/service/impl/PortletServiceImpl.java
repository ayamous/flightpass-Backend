

package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.exception.FlightPassConfigNotFound;
import ma.itroad.ram.flightpass.model.bean.PortletBean;
import ma.itroad.ram.flightpass.model.entity.P_SegmentRef;
import ma.itroad.ram.flightpass.model.entity.Portlet;
import ma.itroad.ram.flightpass.model.enums.PageName;
import ma.itroad.ram.flightpass.model.mapper.PortletMapper;
import ma.itroad.ram.flightpass.repository.PortletRepository;
import ma.itroad.ram.flightpass.repository.SegmentRepository;
import ma.itroad.ram.flightpass.service.FileStorageService;
import ma.itroad.ram.flightpass.service.PortletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
@AllArgsConstructor
public class PortletServiceImpl implements PortletService {
    private FileStorageService fileStorageService;
    private SegmentRepository segmentRepository;
    private PortletRepository portletRepository;
    private PortletMapper portletMapper;


    public void isSegmentExist(Long segmentId) {
        if (!segmentRepository.findById(segmentId).isPresent()) {
            throw new FlightPassConfigNotFound("This segment does not exist ");
        }
    }

    @Override
    public PortletBean postPortlet(PortletBean portletBean, MultipartFile file) {
        isSegmentExist(portletBean.getP_segmentRef_Id());
        String fileName = fileStorageService.storeFile(file);
        String imagePath = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/public/api/portlet/downloadFile/")
                .path(fileName)
                .toUriString();

        Long segmentId = portletBean.getP_segmentRef_Id();
        P_SegmentRef p_segmentRef = segmentRepository.findById(segmentId).get();
        Portlet portlet = new Portlet(portletBean.getPosition(), imagePath, portletBean.getMessage(), portletBean.getTitle(), portletBean.getDescription(), portletBean.getPageName(), portletBean.getImageDisplayingOrder(), p_segmentRef);
        portletRepository.save(portlet);
        PortletBean portletBeanResponse = portletMapper.portletEntitytoBean(portlet);
        portletBeanResponse.setP_segmentRef_Id(portlet.getSegmentRef().getId());
        return portletBeanResponse;
    }

    @Override
    public List<PortletBean> getPortlets() {
        return portletRepository.findAll().stream().map(portlet -> {
            PortletBean portletBean = portletMapper.portletEntitytoBean(portlet);
            portletBean.setP_segmentRef_Id(portlet.getSegmentRef().getId());
            return portletBean;
        }).collect(Collectors.toList());
    }

    @Override
    public List<PortletBean> getPortletsBySegmentId(Long segmentId) {
        isSegmentExist(segmentId);
        return portletRepository.findBySegmentRef_Id(segmentId).stream().map(portlet -> {
            PortletBean portletBean = portletMapper.portletEntitytoBean(portlet);
            portletBean.setP_segmentRef_Id(portlet.getSegmentRef().getId());
            return portletBean;
        }).collect(Collectors.toList());
    }

    @Override
    public List<PortletBean> getPortletsByPageName(PageName pageName) {

        return portletRepository.findByPageName(pageName).stream().map(portlet -> {
            PortletBean portletBean = portletMapper.portletEntitytoBean(portlet);
            portletBean.setP_segmentRef_Id(portlet.getSegmentRef().getId());
            return portletBean;
        }).collect(Collectors.toList());
    }

    @Override
    public List<PortletBean> getPortletsBySegmentIdAndPageName(Long segmentId, PageName pageName) {
        isSegmentExist(segmentId);
        return portletRepository.findBySegmentRef_IdAndPageName(segmentId,pageName).stream().map(portlet -> {
            PortletBean portletBean = portletMapper.portletEntitytoBean(portlet);
            portletBean.setP_segmentRef_Id(portlet.getSegmentRef().getId());
            return portletBean;
        }).collect(Collectors.toList());
    }


    @Override
    public PortletBean getPortletById(Long portletId) {
        Portlet portlet = portletRepository.getById(portletId);
        PortletBean portletBean = portletMapper.portletEntitytoBean(portlet);
        portletBean.setP_segmentRef_Id(portlet.getSegmentRef().getId());
        return portletBean;
    }

    @Override
    public void deletePortletById(Long portletId) {
        portletRepository.deleteById(portletId);
    }

    @Override
    public PortletBean patchPortletById(Long id, PortletBean portletBean, MultipartFile file) {
        Portlet portlet =portletRepository.getById(id);
        if (portletBean.getDescription()!=null){
            portlet.setDescription(portletBean.getDescription());
        }
        if (portletBean.getMessage()!=null){
            portlet.setMessage(portletBean.getMessage());
        }
        if (portletBean.getTitle()!=null){
            portlet.setTitle(portletBean.getTitle());
        }
        if (portletBean.getPosition()!=null){
            portlet.setPosition(portletBean.getPosition());
        }
        if (portletBean.getPageName()!=null){
            portlet.setPageName(portletBean.getPageName());
        }
        if (portletBean.getImageDisplayingOrder()!=null){
            portlet.setImageDisplayingOrder(portletBean.getImageDisplayingOrder());
        }
        if (file !=null){
            String imagePath = fileStorageService.storeFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/portlet/downloadFile/")
                    .path(imagePath)
                    .toUriString();
            portlet.setImagePath(fileDownloadUri);
        }
        portletRepository.save(portlet);
        PortletBean portletBeanResponse = portletMapper.portletEntitytoBean(portlet);
        portletBeanResponse.setP_segmentRef_Id(portlet.getSegmentRef().getId());
        return portletBeanResponse;
    }
}























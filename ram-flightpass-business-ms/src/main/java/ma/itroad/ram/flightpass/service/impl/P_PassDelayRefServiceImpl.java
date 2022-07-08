package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.core.model.entity.BaseEntity;
import ma.itroad.ram.flightpass.exception.ConfiguratioNotFound;
import ma.itroad.ram.flightpass.exception.FlightPassConfigNotFound;
import ma.itroad.ram.flightpass.exception.PostConfiguratiotNotAcceptable;
import ma.itroad.ram.flightpass.model.bean.P_PassDelayRefBean;
import ma.itroad.ram.flightpass.model.entity.P_FlightPassConfig;
import ma.itroad.ram.flightpass.model.entity.P_PassDelayRef;
import ma.itroad.ram.flightpass.model.mapper.P_PassDelayRefMapper;
import ma.itroad.ram.flightpass.repository.P_FlightPassConfigRepository;
import ma.itroad.ram.flightpass.repository.P_PassDelayRefRepository;
import ma.itroad.ram.flightpass.service.P_PassDelayRefService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class P_PassDelayRefServiceImpl implements P_PassDelayRefService {

    private P_PassDelayRefRepository p_passDelayRefRepository;
    private P_PassDelayRefMapper p_passDelayRefMapper;
    private P_FlightPassConfigRepository p_flightPassConfigRepository;

    public void isFlightPassConfigExist(Long configId){
        if (!p_flightPassConfigRepository.findById(configId).isPresent()){
            throw new FlightPassConfigNotFound("This configuration does not exist ");
        }
    }

    @Override
    public P_PassDelayRefBean postPassDelayRef(Long configId, int nbrMonths, double percentage) {
        P_PassDelayRef p_passDelayRef;
        if (p_passDelayRefRepository.findByNbrMonthsAndPercentage(nbrMonths, percentage) != null) {
            p_passDelayRef = p_passDelayRefRepository.findByNbrMonthsAndPercentage(nbrMonths, percentage);
            if (configId != null) {
                isFlightPassConfigExist(configId);
                if (p_passDelayRefRepository.findByNbrMonthsAndFlightPassConfigs_Id(nbrMonths, configId) != null) {
                    throw new PostConfiguratiotNotAcceptable("The percentage of this number of days already exists in this configuration");
                } else {
                    P_FlightPassConfig p_flightPassConfig = p_flightPassConfigRepository.getById(configId);
                    p_flightPassConfig.getP_passDelayRefs().add(p_passDelayRef);
                }
            } else {
               return p_passDelayRefMapper.p_PassDelayRefMapperEntitytoBean(p_passDelayRef);
            }
        } else {
            p_passDelayRef = p_passDelayRefRepository.save(new P_PassDelayRef(nbrMonths, percentage));
            if (configId != null) {
                isFlightPassConfigExist(configId);
                if (p_passDelayRefRepository.findByNbrMonthsAndFlightPassConfigs_Id(nbrMonths, configId) != null) {
                    throw new PostConfiguratiotNotAcceptable("The percentage of this number of days already exists in this configuration") {
                    };
                } else {
                    P_FlightPassConfig p_flightPassConfig = p_flightPassConfigRepository.getById(configId);
                    p_flightPassConfig.getP_passDelayRefs().add(p_passDelayRef);
                }
            }
        }
        return p_passDelayRefMapper.p_PassDelayRefMapperEntitytoBean(p_passDelayRef);
    }

    @Override
    public List<P_PassDelayRefBean> getPassDelayRef() {
        return p_passDelayRefRepository.findAll().stream().map(p_passDelayRef -> {
            P_PassDelayRefBean p_passDelayRefBean = p_passDelayRefMapper.p_PassDelayRefMapperEntitytoBean(p_passDelayRef);
            p_passDelayRefBean.setFlightPassConfigs_Id(p_passDelayRef.getFlightPassConfigs().stream().map(BaseEntity::getId).collect(Collectors.toList()));
            return p_passDelayRefBean;
        }).collect(Collectors.toList());
    }

    @Override
    public List<P_PassDelayRefBean> getPassDelayRefByConfigId(Long configId) {
        isFlightPassConfigExist(configId);
        return p_passDelayRefRepository.findByFlightPassConfigs_Id(configId).stream().map(p_passDelayRef -> {
            P_PassDelayRefBean p_passDelayRefBean = p_passDelayRefMapper.p_PassDelayRefMapperEntitytoBean(p_passDelayRef);
            p_passDelayRefBean.setFlightPassConfigs_Id(p_passDelayRef.getFlightPassConfigs().stream().map(BaseEntity::getId).collect(Collectors.toList()));
            return p_passDelayRefBean;
        }).collect(Collectors.toList());
    }

    @Override
    public P_PassDelayRefBean getMinPassDelay(Long configId) {
        return getPassDelayRefByConfigId(configId).stream().min(Comparator.comparing(P_PassDelayRefBean::getNbrMonths)).
                orElseThrow(NoSuchElementException::new);
    }

    @Override
    public P_PassDelayRefBean getMaxPassDelay(Long configId) {
        return getPassDelayRefByConfigId(configId).stream().max(Comparator.comparing(P_PassDelayRefBean::getNbrMonths)).
                orElseThrow(NoSuchElementException::new);

    }

    @Override
    public List<P_PassDelayRefBean> getPassDelayRefByNbrMonths(int nbrMonths) {
        return p_passDelayRefRepository.findByNbrMonths(nbrMonths).stream().map(p_passDelayRef -> {
            P_PassDelayRefBean p_passDelayRefBean = p_passDelayRefMapper.p_PassDelayRefMapperEntitytoBean(p_passDelayRef);
            p_passDelayRefBean.setFlightPassConfigs_Id(p_passDelayRef.getFlightPassConfigs().stream().map(BaseEntity::getId).collect(Collectors.toList()));
            return p_passDelayRefBean;
        }).collect(Collectors.toList());
    }

    @Override
    public P_PassDelayRefBean getPassDelayRefByNbrMonthsAndConfigId(Long configId, int nbrMonths) {
        isFlightPassConfigExist(configId);
        if (p_passDelayRefRepository.findByNbrMonths(nbrMonths).isEmpty()) {
            return null;
        }
        P_PassDelayRef p_passDelayRef = p_passDelayRefRepository.findByNbrMonthsAndFlightPassConfigs_Id(nbrMonths, configId);
        P_PassDelayRefBean p_passDelayRefBean = p_passDelayRefMapper.p_PassDelayRefMapperEntitytoBean(p_passDelayRef);
        p_passDelayRefBean.setFlightPassConfigs_Id(p_passDelayRef.getFlightPassConfigs().stream().map(BaseEntity::getId).collect(Collectors.toList()));
        return p_passDelayRefBean;
    }

    @Override
    public P_PassDelayRefBean patchPassDelayRefByConfigId(Long configId, int nbrMonths, double percentage) {
            isFlightPassConfigExist(configId);
            if (p_passDelayRefRepository.findByNbrMonthsAndFlightPassConfigs_Id(nbrMonths, configId) == null) {
                throw new ConfiguratioNotFound("this parameter does not exist in this configuration");
            }
            P_FlightPassConfig p_flightPassConfig = p_flightPassConfigRepository.getById(configId);
            P_PassDelayRef p_passDelayRef = p_passDelayRefRepository.findByNbrMonthsAndFlightPassConfigs_Id(nbrMonths, configId);
            p_flightPassConfig.getP_passDelayRefs().remove(p_passDelayRef);
            p_flightPassConfigRepository.save(p_flightPassConfig);

            P_PassDelayRefBean p_passDelayRefBean =  postPassDelayRef(configId ,nbrMonths ,percentage);
            List<Long> configList = new ArrayList<>();
            configList.add(configId);
            p_passDelayRefBean.setFlightPassConfigs_Id(configList);
            return p_passDelayRefBean;
    }

    @Override
    public void deletePassDelayRefByNbrMonthsAndConfigId(Long configId, int nbrMonths) {
        isFlightPassConfigExist(configId);
        P_FlightPassConfig p_flightPassConfig = p_flightPassConfigRepository.getById(configId);
        P_PassDelayRef p_passDelayRef = p_passDelayRefRepository.findByNbrMonthsAndFlightPassConfigs_Id(nbrMonths, configId);
        p_flightPassConfig.getP_passDelayRefs().remove(p_passDelayRef);
        p_flightPassConfigRepository.save(p_flightPassConfig);
    }

}


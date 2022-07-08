package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.core.model.entity.BaseEntity;
import ma.itroad.ram.flightpass.exception.ConfiguratioNotFound;
import ma.itroad.ram.flightpass.exception.FlightPassConfigNotFound;
import ma.itroad.ram.flightpass.exception.PostConfiguratiotNotAcceptable;
import ma.itroad.ram.flightpass.model.bean.P_DayToDepartureRefBean;
import ma.itroad.ram.flightpass.model.entity.P_DayToDepartureRef;
import ma.itroad.ram.flightpass.model.entity.P_FlightPassConfig;
import ma.itroad.ram.flightpass.model.mapper.P_DayToDepartureRefMapper;
import ma.itroad.ram.flightpass.repository.P_DayToDepartureRefRepository;
import ma.itroad.ram.flightpass.repository.P_FlightPassConfigRepository;
import ma.itroad.ram.flightpass.service.P_DayToDepartureRefService;
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
public class P_DayToDepartureRefServiceImpl implements P_DayToDepartureRefService {

    private P_DayToDepartureRefRepository p_dayToDepartureRefRepository;
    private P_DayToDepartureRefMapper p_dayToDepartureRefMapper;
    private P_FlightPassConfigRepository p_flightPassConfigRepository;



     public void isFlightPassConfigExist(Long configId){
        if (!p_flightPassConfigRepository.findById(configId).isPresent()){
            throw new FlightPassConfigNotFound("This configuration does not exist ");
        }
    }

    public P_DayToDepartureRefBean postDayToDepartureRef(Long configId, int nbrDays, double percentage) {
        P_DayToDepartureRef p_dayToDepartureRef;
        if (p_dayToDepartureRefRepository.getByNbrDaysAndPercentage(nbrDays, percentage) != null) {
            p_dayToDepartureRef = p_dayToDepartureRefRepository.getByNbrDaysAndPercentage(nbrDays, percentage);
            if (configId != null) {
                isFlightPassConfigExist(configId);
                if (p_dayToDepartureRefRepository.findByNbrDaysAndFlightPassConfigs_Id(nbrDays, configId) != null) {
                    throw new PostConfiguratiotNotAcceptable("The percentage of this number of days already exists in this configuration");
                } else {
                    P_FlightPassConfig p_flightPassConfig = p_flightPassConfigRepository.getById(configId);
                    p_flightPassConfig.getP_dayToDepartureRefs().add(p_dayToDepartureRef);
                }
            } else {
                return p_dayToDepartureRefMapper.p_BaseConfigBeanEntitytoBean(p_dayToDepartureRef);
            }
        } else {
            p_dayToDepartureRef = p_dayToDepartureRefRepository.save(new P_DayToDepartureRef(nbrDays, percentage));
            if (configId != null) {
                isFlightPassConfigExist(configId);
                if (p_dayToDepartureRefRepository.findByNbrDaysAndFlightPassConfigs_Id(nbrDays, configId) != null) {
                    throw new PostConfiguratiotNotAcceptable("The percentage of this number of days already exists in this configuration") {
                    };
                } else {
                    P_FlightPassConfig p_flightPassConfig = p_flightPassConfigRepository.getById(configId);
                    p_flightPassConfig.getP_dayToDepartureRefs().add(p_dayToDepartureRef);
                }
            }
        }
        return p_dayToDepartureRefMapper.p_BaseConfigBeanEntitytoBean(p_dayToDepartureRef);
    }

    @Override
    public List<P_DayToDepartureRefBean> getDayToDepartureRef() {
        return p_dayToDepartureRefRepository.findAll().stream().map(p_dayToDepartureRef -> {
            P_DayToDepartureRefBean p_dayToDepartureRefBean = p_dayToDepartureRefMapper.p_BaseConfigBeanEntitytoBean(p_dayToDepartureRef);
            p_dayToDepartureRefBean.setConfigIds(p_dayToDepartureRef.getFlightPassConfigs().stream().map(BaseEntity::getId).collect(Collectors.toList()));
            return p_dayToDepartureRefBean;
        }).collect(Collectors.toList());
    }


    @Override
    public List<P_DayToDepartureRefBean> getDayToDepartureRefByConfigId(Long configId) {
        isFlightPassConfigExist(configId);
        return p_dayToDepartureRefRepository.findByFlightPassConfigs_Id(configId).stream().map(p_dayToDepartureRef -> {
            P_DayToDepartureRefBean p_dayToDepartureRefBean = p_dayToDepartureRefMapper.p_BaseConfigBeanEntitytoBean(p_dayToDepartureRef);
            p_dayToDepartureRefBean.setConfigIds(p_dayToDepartureRef.getFlightPassConfigs().stream().map(BaseEntity::getId).collect(Collectors.toList()));
            return p_dayToDepartureRefBean;
        }).collect(Collectors.toList());
    }

    @Override
    public P_DayToDepartureRefBean getMaxDayToDeparture(Long configId) {
        return getDayToDepartureRefByConfigId(configId).stream().max(Comparator.comparing(P_DayToDepartureRefBean::getNbrDays)).
                orElseThrow(NoSuchElementException::new);

    }

    @Override
    public P_DayToDepartureRefBean getMinDayToDeparture(Long configId) {
        return getDayToDepartureRefByConfigId(configId).stream().min(Comparator.comparing(P_DayToDepartureRefBean::getNbrDays)).
                orElseThrow(NoSuchElementException::new);

    }

    @Override
    public List<P_DayToDepartureRefBean> getDayToDepartureRefByNbrDays(int days) {
        return p_dayToDepartureRefRepository.findByNbrDays(days).stream().map(p_dayToDepartureRef -> {
            P_DayToDepartureRefBean p_dayToDepartureRefBean = p_dayToDepartureRefMapper.p_BaseConfigBeanEntitytoBean(p_dayToDepartureRef);
            p_dayToDepartureRefBean.setConfigIds(p_dayToDepartureRef.getFlightPassConfigs().stream().map(BaseEntity::getId).collect(Collectors.toList()));
            return p_dayToDepartureRefBean;
        }).collect(Collectors.toList());
    }

    @Override
    public P_DayToDepartureRefBean getDayToDepartureRefByNbrDaysAndConfigId(Long configId, int days) {
        isFlightPassConfigExist(configId);
        if (p_dayToDepartureRefRepository.findByNbrDaysAndFlightPassConfigs_Id(days, configId) == null) {
            return null;
        }
        P_DayToDepartureRef p_dayToDepartureRef = p_dayToDepartureRefRepository.findByNbrDaysAndFlightPassConfigs_Id(days, configId);
        P_DayToDepartureRefBean p_dayToDepartureRefBean = p_dayToDepartureRefMapper.p_BaseConfigBeanEntitytoBean(p_dayToDepartureRef);
        p_dayToDepartureRefBean.setConfigIds(p_dayToDepartureRef.getFlightPassConfigs().stream().map(BaseEntity::getId).collect(Collectors.toList()));
        return p_dayToDepartureRefBean;
    }


    @Override
    public P_DayToDepartureRefBean patchDayToDepartureRef(Long configId, int nbrDays, double percentage) {
        if (configId != null) {
            isFlightPassConfigExist(configId);
            if (p_dayToDepartureRefRepository.findByNbrDaysAndFlightPassConfigs_Id(nbrDays, configId) == null) {
                throw new ConfiguratioNotFound("this parameter does not exist in this configuration");
            }
            P_FlightPassConfig p_flightPassConfig = p_flightPassConfigRepository.getById(configId);
            P_DayToDepartureRef p_dayToDepartureRef = p_dayToDepartureRefRepository.findByNbrDaysAndFlightPassConfigs_Id(nbrDays, configId);
            p_flightPassConfig.getP_dayToDepartureRefs().remove(p_dayToDepartureRef);
            p_flightPassConfigRepository.save(p_flightPassConfig);
            P_DayToDepartureRefBean p_dayToDepartureRefBean =  postDayToDepartureRef(configId ,nbrDays ,percentage);
            List<Long> configList = new ArrayList<>();
            configList.add(configId);
            p_dayToDepartureRefBean.setConfigIds(configList);
            return p_dayToDepartureRefBean;

        } else {
            if (p_dayToDepartureRefRepository.findByNbrDays(nbrDays) == null) {
                throw new ConfiguratioNotFound("this parameter does not exist");
            }
            P_DayToDepartureRef p_dayToDepartureRef = p_dayToDepartureRefRepository.findByNbrDaysAndFlightPassConfigs_Id(nbrDays, configId);
            p_dayToDepartureRef.setPercentage(percentage);
            p_dayToDepartureRefRepository.save(p_dayToDepartureRef);
            return p_dayToDepartureRefMapper.p_BaseConfigBeanEntitytoBean(p_dayToDepartureRef);
        }
    }

    @Override
    public void deleteDayToDepartureRefByNbrDaysAndConfigId(Long configId, int nbrDays) {
        isFlightPassConfigExist(configId);
        P_FlightPassConfig p_flightPassConfig = p_flightPassConfigRepository.getById(configId);
        P_DayToDepartureRef p_dayToDepartureRef = p_dayToDepartureRefRepository.findByNbrDaysAndFlightPassConfigs_Id(nbrDays, configId);
        p_flightPassConfig.getP_dayToDepartureRefs().remove(p_dayToDepartureRef);
        p_dayToDepartureRefRepository.save(p_dayToDepartureRef);
        p_flightPassConfigRepository.save(p_flightPassConfig);

    }


}

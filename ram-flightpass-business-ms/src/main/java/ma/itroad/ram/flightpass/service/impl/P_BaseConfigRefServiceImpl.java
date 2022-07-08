package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.core.model.entity.BaseEntity;
import ma.itroad.ram.flightpass.exception.ConfiguratioNotFound;
import ma.itroad.ram.flightpass.exception.FlightPassConfigNotFound;
import ma.itroad.ram.flightpass.exception.PostConfiguratiotNotAcceptable;
import ma.itroad.ram.flightpass.model.bean.P_BaseConfigRefBean;
import ma.itroad.ram.flightpass.model.entity.P_BaseConfigRef;
import ma.itroad.ram.flightpass.model.entity.P_FlightPassConfig;
import ma.itroad.ram.flightpass.model.mapper.P_BaseConfigRefMapper;
import ma.itroad.ram.flightpass.repository.P_BaseConfigRefRepository;
import ma.itroad.ram.flightpass.repository.P_FlightPassConfigRepository;
import ma.itroad.ram.flightpass.service.P_BaseConfigRefService;
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
public class P_BaseConfigRefServiceImpl implements P_BaseConfigRefService {

    private P_BaseConfigRefRepository p_baseConfigRefRepository;
    private P_BaseConfigRefMapper p_baseConfigRefMapper;
    private P_FlightPassConfigRepository p_flightPassConfigRepository;

    public void isFlightPassConfigExist(Long configId) {
        if (!p_flightPassConfigRepository.findById(configId).isPresent()) {
            throw new FlightPassConfigNotFound("This configuration does not exist ");
        }
    }


    @Override
    public P_BaseConfigRefBean postBaseConfigRef(Long configId, int nbrFlights, double percentage) {
        P_BaseConfigRef p_baseConfigRef;
        if (p_baseConfigRefRepository.findByNbrFlightsAndPercentage(nbrFlights, percentage) != null) {
            p_baseConfigRef = p_baseConfigRefRepository.findByNbrFlightsAndPercentage(nbrFlights, percentage);
            if (configId != null) {
                isFlightPassConfigExist(configId);
                if (p_baseConfigRefRepository.findByNbrFlightsAndFlightPassConfigs_Id(nbrFlights, configId) != null) {
                    throw new PostConfiguratiotNotAcceptable("The percentage of this number of days already exists in this configuration");
                } else {
                    P_FlightPassConfig p_flightPassConfig = p_flightPassConfigRepository.getById(configId);
                    p_flightPassConfig.getP_baseConfigRefs().add(p_baseConfigRef);
                }
            } else {
                return p_baseConfigRefMapper.p_BaseConfigBeanEntitytoBean(p_baseConfigRef);
            }
        } else {
            p_baseConfigRef = p_baseConfigRefRepository.save(new P_BaseConfigRef(nbrFlights, percentage));
            if (configId != null) {
                isFlightPassConfigExist(configId);
                if (p_baseConfigRefRepository.findByNbrFlightsAndFlightPassConfigs_Id(nbrFlights, configId) != null) {
                    throw new PostConfiguratiotNotAcceptable("The percentage of this number of days already exists in this configuration") {
                    };
                } else {
                    P_FlightPassConfig p_flightPassConfig = p_flightPassConfigRepository.getById(configId);
                    p_flightPassConfig.getP_baseConfigRefs().add(p_baseConfigRef);
                }
            }
        }
        return p_baseConfigRefMapper.p_BaseConfigBeanEntitytoBean(p_baseConfigRef);
    }

    @Override
    public List<P_BaseConfigRefBean> getBaseConfigRef() {
        return p_baseConfigRefRepository.findAll().stream().map(baseConfigRef -> {
            P_BaseConfigRefBean p_baseConfigRefBean = p_baseConfigRefMapper.p_BaseConfigBeanEntitytoBean(baseConfigRef);
            p_baseConfigRefBean.setFlightPassConfigs_Id(baseConfigRef.getFlightPassConfigs().stream().map(BaseEntity::getId).collect(Collectors.toList()));
            return p_baseConfigRefBean;
        }).collect(Collectors.toList());
    }

    @Override
    public List<P_BaseConfigRefBean> getBaseConfigRefByConfigId(Long configId) {
        isFlightPassConfigExist(configId);
        return p_baseConfigRefRepository.findByFlightPassConfigs_Id(configId).stream().map(p_baseConfigRef -> {
            P_BaseConfigRefBean p_baseConfigRefBean = p_baseConfigRefMapper.p_BaseConfigBeanEntitytoBean(p_baseConfigRef);
            p_baseConfigRefBean.setFlightPassConfigs_Id(p_baseConfigRef.getFlightPassConfigs().stream().map(BaseEntity::getId).collect(Collectors.toList()));
            return p_baseConfigRefBean;
        }).collect(Collectors.toList());
    }

    @Override
    public P_BaseConfigRefBean maxBaseConfig(Long configId) {
        return getBaseConfigRefByConfigId(configId).stream().max(Comparator.comparing(P_BaseConfigRefBean::getNbrFlights)).
                orElseThrow(NoSuchElementException::new);
    }

    @Override
    public P_BaseConfigRefBean minBaseConfig(Long configId) {
        return getBaseConfigRefByConfigId(configId).stream().min(Comparator.comparing(P_BaseConfigRefBean::getNbrFlights)).
                orElseThrow(NoSuchElementException::new);

    }

    @Override
    public List<P_BaseConfigRefBean> getBaseConfigRefByNbrFlights(int nbrFlights) {
        return p_baseConfigRefRepository.findByNbrFlights(nbrFlights).stream().map(p_baseConfigRef -> {
            P_BaseConfigRefBean p_baseConfigRefBean = p_baseConfigRefMapper.p_BaseConfigBeanEntitytoBean(p_baseConfigRef);
            p_baseConfigRefBean.setFlightPassConfigs_Id(p_baseConfigRef.getFlightPassConfigs().stream().map(BaseEntity::getId).collect(Collectors.toList()));
            return p_baseConfigRefBean;
        }).collect(Collectors.toList());
    }

    @Override
    public P_BaseConfigRefBean getBaseConfigRefByNbrFlightsAndConfigId(Long configId, int nbrFlights) {
        isFlightPassConfigExist(configId);
        if (p_baseConfigRefRepository.findByNbrFlightsAndFlightPassConfigs_Id(nbrFlights, configId) == null) {
            return null;
        }
        P_BaseConfigRef p_baseConfigRef = p_baseConfigRefRepository.findByNbrFlightsAndFlightPassConfigs_Id(nbrFlights, configId);

        P_BaseConfigRefBean p_baseConfigRefBean = p_baseConfigRefMapper.p_BaseConfigBeanEntitytoBean(p_baseConfigRef);
        p_baseConfigRefBean.setFlightPassConfigs_Id(p_baseConfigRef.getFlightPassConfigs().stream().map(BaseEntity::getId).collect(Collectors.toList()));
        return p_baseConfigRefBean;
    }

    @Override
    public P_BaseConfigRefBean patchBaseConfigRef(Long configId, int nbrFlights, double percentage) {
        isFlightPassConfigExist(configId);
        if (p_baseConfigRefRepository.findByNbrFlightsAndFlightPassConfigs_Id(nbrFlights, configId) == null) {
            throw new ConfiguratioNotFound("This parameter does not exist in this configuration");
        }
        P_FlightPassConfig p_flightPassConfig = p_flightPassConfigRepository.getById(configId);
        P_BaseConfigRef p_baseConfigRef = p_baseConfigRefRepository.findByNbrFlightsAndFlightPassConfigs_Id(nbrFlights, configId);
        p_flightPassConfig.getP_baseConfigRefs().remove(p_baseConfigRef);
        p_flightPassConfigRepository.save(p_flightPassConfig);

        P_BaseConfigRefBean p_baseConfigRefBean = postBaseConfigRef(configId, nbrFlights, percentage);
        List<Long> configList = new ArrayList<>();
        configList.add(configId);
        p_baseConfigRefBean.setFlightPassConfigs_Id(configList);
        return p_baseConfigRefBean;
    }

    @Override
    public void deleteBaseConfigRefByNbrFlightsAndConfigId(Long configId, int nbrFlights) {
        isFlightPassConfigExist(configId);
        P_FlightPassConfig p_flightPassConfig = p_flightPassConfigRepository.getById(configId);
        P_BaseConfigRef p_baseConfigRef = p_baseConfigRefRepository.findByNbrFlightsAndFlightPassConfigs_Id(nbrFlights, configId);
        p_flightPassConfig.getP_baseConfigRefs().remove(p_baseConfigRef);
        p_flightPassConfigRepository.save(p_flightPassConfig);
    }
}

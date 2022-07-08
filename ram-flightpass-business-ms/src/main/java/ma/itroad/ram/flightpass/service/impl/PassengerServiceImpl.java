package ma.itroad.ram.flightpass.service.impl;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.PassengerBean;
import ma.itroad.ram.flightpass.model.entity.D_Passenger;
import ma.itroad.ram.flightpass.model.mapper.PassengerMapper;
import ma.itroad.ram.flightpass.repository.PassengerRepository;
import ma.itroad.ram.flightpass.service.PassengerService;
import ma.itroad.ram.flightpass.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private PassengerRepository passengerRepository;
    private UserService userService;
    private PassengerMapper passengerMapper;

    @Override
    public D_Passenger createPassenger(PassengerBean passengerBean) {

        String userAccountId = userService.getUserAccountId();
        D_Passenger byEmailAndUserAccountId = passengerRepository.findByEmailAndUserAccountId(passengerBean.getEmail(), userAccountId);
        D_Passenger byEmail = passengerRepository.findByEmail(passengerBean.getEmail());


        if (userAccountId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (byEmail != null) {

            if (byEmailAndUserAccountId == null && byEmail.getUserAccountId() != null) {

                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Désolé, ce Passager est lié à un autre profil, Veuillez changer l'email");


            } else if (byEmail.getUserAccountId() == null) {

                //passenger exist but is not related to any account

                byEmail.setUserAccountId(userAccountId);

            } else {

                return byEmailAndUserAccountId;
            }
        }

        D_Passenger passenger = passengerMapper.beanToEntity(passengerBean);

        String emailKC = userService.getAccessToken().getEmail();
        passenger.setPrincipalUserAccount(emailKC.equals(passenger.getEmail()));
        passenger.setUserAccountId(userAccountId);


        return passengerRepository.save(passenger);

    }

    @Override
    public List<PassengerBean> getPassengerByUserId() {
        String userAccountId = userService.getUserAccountId();
        return passengerRepository.findByUserAccountIdAndPrincipalUserAccountIsFalse(userAccountId)
                .stream().map(passengerMapper::entityToBean).collect(Collectors.toList());
    }
}
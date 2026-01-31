package com.labourseSearching.Service.CustomerService;

import com.labourseSearching.DTO.AddressDtos.LocationUpdateDto;
import com.labourseSearching.DTO.CustomerDto.CustomerProfileDto;
import com.labourseSearching.DTO.CustomerDto.CustomerProfileResponceDto;
import com.labourseSearching.Entity.Customer.CustomerProfile;
import com.labourseSearching.Entity.ENUM.UserType;
import com.labourseSearching.Entity.LocationLiveTracing.LiveLocation;
import com.labourseSearching.Entity.Users.User;
import com.labourseSearching.GlobalExCeption.ExceptionInterface.UserNotFoudnException;
import com.labourseSearching.Repository.Customer.CustomerProfileRepository;
import com.labourseSearching.Repository.UserRepository.UserRepository;
import org.springframework.stereotype.Service;
@Service
public class CustomerServiceImp implements CustomerService {

    private final CustomerProfileRepository customerProfileRepository;
    private final UserRepository userRepository;

    public CustomerServiceImp(
            CustomerProfileRepository customerProfileRepository,
            UserRepository userRepository
    ) {
        this.customerProfileRepository = customerProfileRepository;
        this.userRepository = userRepository;
    }

    // ================= CREATE PROFILE =================
    @Override
    public CustomerProfileResponceDto createProfile(
            long userId,
            CustomerProfileDto dto
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoudnException("User not found"));

        // 🚫 USER TYPE CHECK
        if (user.getUserType() != UserType.CUSTOMER) {
            throw new IllegalStateException("User is not CUSTOMER");
        }

        // 🚫 DUPLICATE PROFILE CHECK
        if (user.getCustomerProfile() != null) {
            throw new IllegalStateException("Customer profile already exists");
        }

        CustomerProfile profile = new CustomerProfile();
        profile.setUser(user);

        customerProfileRepository.save(profile);

        CustomerProfileResponceDto response = new CustomerProfileResponceDto();
        response.setProfileId(profile.getId());
        response.setUserId(user.getId());

        return response;
    }


    // ================= LIVE LOCATION =================
    @Override
    public LocationUpdateDto updateLiveLocation(
            long userId,
            LocationUpdateDto liveDto
    ) {

        CustomerProfile profile = customerProfileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Create customer profile first"));

        LiveLocation location = profile.getLiveLocation();
        if (location == null) {
            location = new LiveLocation();
        }

        location.setLatitude(liveDto.getLatitude());
        location.setLongitude(liveDto.getLongitude());

        profile.setLiveLocation(location);
        customerProfileRepository.save(profile);

        return liveDto;
    }
}
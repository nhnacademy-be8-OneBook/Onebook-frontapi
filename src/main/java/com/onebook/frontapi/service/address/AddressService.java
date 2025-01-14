package com.onebook.frontapi.service.address;

import com.onebook.frontapi.dto.address.request.AddMemberAddressRequest;
import com.onebook.frontapi.dto.address.request.DeleteMemberAddressRequest;
import com.onebook.frontapi.dto.address.request.UpdateMemberAddressRequest;
import com.onebook.frontapi.dto.address.response.MemberAddressResponse;
import com.onebook.frontapi.feign.address.AddressClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressClient addressClient;

    public MemberAddressResponse addMemberAddress(AddMemberAddressRequest addMemberAddressRequest) {
        return addressClient.addMemberAddress(addMemberAddressRequest).getBody();
    }

    public MemberAddressResponse getMemberAddressById(Long id){
        return addressClient.getMemberAddressById(id).getBody();
    }

    public List<MemberAddressResponse> getAllMemberAddressByMemberId() {
        return addressClient.getAllMemberAddressByMemberId().getBody();
    }

    public MemberAddressResponse updateMemberAddress(UpdateMemberAddressRequest updateMemberAddressRequest){
        return addressClient.updateMemberAddressById(updateMemberAddressRequest).getBody();
    }

    public MemberAddressResponse deleteMemberAddress(DeleteMemberAddressRequest deleteMemberAddressRequest){
        return addressClient.deleteMemberAddress(deleteMemberAddressRequest).getBody();
    }


}

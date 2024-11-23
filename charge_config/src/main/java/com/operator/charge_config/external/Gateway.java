package com.operator.charge_config.external;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.operator.charge_config.base.BaseResponse;
import com.operator.charge_config.dto.request.ServiceChargeRequestDto;
import com.operator.charge_config.dto.request.UnlockCodeRequestDto;
import com.operator.charge_config.dto.response.ServiceChargeResponse;
import com.operator.charge_config.dto.response.UnlockCodeResponse;
import com.operator.charge_config.util.Contents;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Service
public class Gateway {

    private final String baseUrl = "http://demo.webmanza.com/a55dbz923ace647v/api/v1.0/services/";
    private final String contentRetrievalEndpoint = "content";
    private final String unlockCodeEndpoint = "unlockCode";
    private final String chargingEndpoint = "charge";

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public List<Contents> getContents(){
        String url = new StringBuilder(baseUrl)
                .append(contentRetrievalEndpoint).toString();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Content-Type","application/json");

        BaseResponse baseResponse = apiClient.get(url, map, BaseResponse.class).block();
//        List<Contents> contentsList = modelMapper.map(baseResponse.getContents(), List.class);
//        return contentsList;
        return objectMapper.convertValue(baseResponse.getContents(), new TypeReference<List<Contents>>() {});

    }

    public UnlockCodeResponse unlockCode(UnlockCodeRequestDto unlockCodeRequestDto){
        String url = new StringBuilder(baseUrl)
                .append(unlockCodeEndpoint).toString();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Content-Type","application/json");

        UnlockCodeResponse unlockCodeResponse = apiClient.post(url, map,unlockCodeRequestDto ,UnlockCodeResponse.class).block();
        return unlockCodeResponse;
    }

    public ServiceChargeResponse performCharging(ServiceChargeRequestDto serviceChargeRequestDto){
        String url = new StringBuilder(baseUrl)
                .append(chargingEndpoint).toString();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Content-Type","application/json");

        ServiceChargeResponse serviceChargeResponse = apiClient.post(url, map,serviceChargeRequestDto ,ServiceChargeResponse.class).block();
        return serviceChargeResponse;
    }

}

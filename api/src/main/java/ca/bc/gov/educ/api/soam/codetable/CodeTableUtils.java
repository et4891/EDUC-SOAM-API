package ca.bc.gov.educ.api.soam.codetable;

import ca.bc.gov.educ.api.soam.model.entity.AccessChannelCodeEntity;
import ca.bc.gov.educ.api.soam.model.entity.IdentityTypeCodeEntity;
import ca.bc.gov.educ.api.soam.properties.ApplicationProperties;
import ca.bc.gov.educ.api.soam.rest.RestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CodeTableUtils {

  private final RestUtils restUtils;

  private final ApplicationProperties props;

  @Autowired
  public CodeTableUtils(final RestUtils restUtils, final ApplicationProperties props) {
    this.restUtils = restUtils;
    this.props = props;
  }

  @Cacheable("accessChannelCodes")
  public Map<String, AccessChannelCodeEntity> getAllAccessChannelCodes() {
    log.info("Fetching all access channel codes");
    RestTemplate restTemplate = restUtils.getRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    ResponseEntity<AccessChannelCodeEntity[]> response;
    response = restTemplate.exchange(
            props.getDigitalIdentifierApiURL() + "/accessChannelCodes", HttpMethod.GET,
            new HttpEntity<>("parameters", headers), AccessChannelCodeEntity[].class);

    Map<String, AccessChannelCodeEntity> map = new HashMap<>();
    if (response.getBody() != null) {
      for (AccessChannelCodeEntity entity : response.getBody()) {
        map.put(entity.getAccessChannelCode(), entity);
      }
    }

    return map;
  }

  @Cacheable("identityTypeCodes")
  public Map<String, IdentityTypeCodeEntity> getAllIdentifierTypeCodes() {
    log.info("Fetching all identity type codes");
    RestTemplate restTemplate = restUtils.getRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    ResponseEntity<IdentityTypeCodeEntity[]> response;
    response = restTemplate.exchange(
            props.getDigitalIdentifierApiURL() + "/identityTypeCodes", HttpMethod.GET,
            new HttpEntity<>("parameters", headers), IdentityTypeCodeEntity[].class);

    Map<String, IdentityTypeCodeEntity> map = new HashMap<>();
    if (response.getBody() != null) {
      for (IdentityTypeCodeEntity entity : response.getBody()) {
        map.put(entity.getIdentityTypeCode(), entity);
      }
    }

    return map;
  }

}

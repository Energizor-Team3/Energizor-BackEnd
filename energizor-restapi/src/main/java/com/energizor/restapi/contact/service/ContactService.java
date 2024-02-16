package com.energizor.restapi.contact.service;

import com.energizor.restapi.contact.dto.CompanyContactDTO;
import com.energizor.restapi.contact.dto.PersonalContactDTO;
import com.energizor.restapi.contact.entity.CompanyContact;
import com.energizor.restapi.contact.entity.PersonalContact;
import com.energizor.restapi.contact.entity.User;
import com.energizor.restapi.contact.repository.CompanyContactRepository;
import com.energizor.restapi.contact.repository.PersonalContactRepository;
import com.energizor.restapi.contact.repository.UserContactRepository;
import com.energizor.restapi.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContactService {

    private final PersonalContactRepository personalContactRepository;
    private final CompanyContactRepository companyContactRepository;
    private final UserContactRepository userRepository;
    private final ModelMapper modelMapper;

    public ContactService(PersonalContactRepository personalContactRepository, CompanyContactRepository companyContactRepository, UserContactRepository userRepository, ModelMapper modelMapper) {
        this.personalContactRepository = personalContactRepository;
        this.companyContactRepository = companyContactRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    /* 회사 연락처 목록 조회 */
    public List<CompanyContactDTO> findAllCompanyList() {
        log.info("companyList start ==============================");
        List<CompanyContact> companyContacts = companyContactRepository.findAll();

        return companyContacts.stream()
                .map(companyContact -> modelMapper.map(companyContact, CompanyContactDTO.class))
                .collect(Collectors.toList());

    }

    /* 회사 연락처 상세 조회 */
    public CompanyContactDTO selectCompany(int cpCode) {
        log.info("selectCompany start ++++++++++++++++++>");
        CompanyContact companyContact = companyContactRepository.findById(cpCode).get();
        CompanyContactDTO companyContactDTO = modelMapper.map(companyContact, CompanyContactDTO.class);
        System.out.println("contact +++++++++++++> " + companyContact);
        log.info("selectCompany End +++++++++++++++>");

        return companyContactDTO;
    }
    /*----------------------------------------------------------------------------------------------------------------*/
    /* 개인 연락처 목록 조회 */
    public List<PersonalContactDTO> getPersonalContactByUserCode(int userCode) {
        User user = userRepository.findById(userCode)
                .orElseThrow(() ->  new NotFoundException("User not found"));
        List<PersonalContact> personalContacts = personalContactRepository.findByUser(user);

        return personalContacts.stream()
                .map(personalContact -> modelMapper.map(personalContact, PersonalContactDTO.class))
                .collect(Collectors.toList());
    }


    /* 개인 연락처 상세 조회 */
    public PersonalContactDTO selectPersonal(int pcCode) {
        log.info("selectPersonal start ------------------------>");

        PersonalContact personalContact = personalContactRepository.findById(pcCode).get();
        PersonalContactDTO personalContactDTO = modelMapper.map(personalContact, PersonalContactDTO.class);

        System.out.println("contact -------------> " + personalContact);

        log.info("selectPersonal End --------------->");

        return personalContactDTO;
    }

    /* 개인 연락처 추가 */
    @Transactional
    public PersonalContactDTO insert(int userCode, PersonalContactDTO personalContactDTO) {
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new NotFoundException("User not found"));
        PersonalContact personalContact = modelMapper.map(personalContactDTO, PersonalContact.class);
        personalContact.setUser(user);
        PersonalContact savedPersonalContact = personalContactRepository.save(personalContact);
        return modelMapper.map(savedPersonalContact, PersonalContactDTO.class);
    }

    /* 개인 연락처 수정 */
    public PersonalContactDTO update(int pcCode, PersonalContactDTO personalContactDTO) {
        // 엔티티를 데이터베이스에서 조회
        PersonalContact personalContact = personalContactRepository.findById(pcCode)
                .orElseThrow(() -> new NotFoundException("Personal contact not found"));

        // ModelMapper를 사용하여 엔티티를 DTO로 변환
        PersonalContact update = modelMapper.map(personalContactDTO, PersonalContact.class);
        update.setPcCode(pcCode);
        // 수정된 개인 연락처를 DTO로 변환하여 반환
        return modelMapper.map(personalContactRepository.save(update), PersonalContactDTO.class);
    }

    /* 개인 연락처 삭제 */
    public PersonalContactDTO delete(int pcCode) {
        PersonalContact personalContact = personalContactRepository.findById(pcCode)
                .orElseThrow(() -> new NotFoundException("Personal contact not found"));

        personalContactRepository.delete(personalContact);
        return modelMapper.map(personalContact, PersonalContactDTO.class);
    }
}

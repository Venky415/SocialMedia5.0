package com.project.socialMedia.service;

import com.project.socialMedia.entity.Member;
import com.project.socialMedia.response.MemberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.socialMedia.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository socialMemberRepository; // Repository for handling members

    // Method to retrieve a member by their ID
    public Member getMemberById(Long memberId) {
        return socialMemberRepository.findById(memberId).orElse(null);
    }

    // Method to retrieve all members and map them to response DTOs
    public List<MemberResponse> getAllMembers() {
        return socialMemberRepository.findAll()
                .stream().map(member -> {
                    MemberResponse memberResponse = new MemberResponse();
                    memberResponse.setMemberId(member.getId());
                    memberResponse.setName(member.getName());
                    memberResponse.setEmail(member.getEmail());
                    return memberResponse;
                }).toList();
    }

    // Method to retrieve a member by their email
    public Optional<Member> getMemberByEmail(String email) {
        return socialMemberRepository.findByEmail(email);
    }

    // Method to create a new member
    public void createMember(String email, String name, String password) {
        Member member = new Member();
        member.setEmail(email);
        member.setName(name);
        member.setPassword(password);
        socialMemberRepository.save(member);
    }
}

package com.wbf.mutuelle.services;

import com.wbf.mutuelle.entities.Member;
import com.wbf.mutuelle.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(Long id){
        return memberRepository.findById(id);
    }

    public Member createMember(Member member){
       return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member memberDetails){
     Member member = memberRepository.findById(id).orElseThrow();
     member.setName(memberDetails.getName());
        member.setFirstName(memberDetails.getFirstName());
        member.setName(memberDetails.getName());
        member.setEmail(memberDetails.getEmail());
        member.setPassword(memberDetails.getPassword());
        member.setNpi(memberDetails.getNpi());
        member.setPhone(memberDetails.getPhone());
        member.setRole(memberDetails.getRole());

        return memberRepository.save(member);
    }

    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }

}

package com.wbf.mutuelle.controllers;

import com.wbf.mutuelle.entities.Member;
import com.wbf.mutuelle.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("mut/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id).orElseThrow();
    }

    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return memberService.createMember(member);
    }

    @PostMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member memberDetails) {
        return memberService.updateMember(id, memberDetails);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
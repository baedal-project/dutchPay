package com.baedal.dutchpay.repositrory;

import com.baedal.dutchpay.entity.Member;
import com.baedal.dutchpay.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);

    Optional<RefreshToken> findByTokenValue(String refreshToken);
}

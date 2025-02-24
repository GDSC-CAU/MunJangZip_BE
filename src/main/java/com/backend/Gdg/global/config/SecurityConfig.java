package com.backend.Gdg.global.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//    private final JwtTokenProvider jwtTokenProvider;
//    private final PrincipalDetailsService principalDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
                                // Member 관련 접근
                                .requestMatchers("/member/register", "/member/login/kakao", "/member/login/naver", "/member/login/email","/member/refresh").permitAll()
                                // Trade 관련 접근
                                .requestMatchers("/trade/", "trade/{trade_id}", "/trade/my/active", "/trade/my/history", "/trade/my/like").permitAll()
                                .requestMatchers("/trade/near/{trade_type}", "/trade/near/recent", "trade/near/near-expiry", "trade/near/far-expiry").permitAll()
                                .requestMatchers("trade/like/{trade_id}", "/trade/like/{trade_id}/delete", "/trade/like/{trade_id}/count").permitAll()
                                .requestMatchers("trade/other-trade", "/trade/keyword/alert").permitAll()
                                // Ingredient 관련 접근
                                .requestMatchers("/ingredient/","/ingredient/{ingredient_id}/purchase-date", "/ingredient/{ingredient_id}/expiry-date").permitAll()
                                .requestMatchers("/ingredient/{ingredient_id}","/ingredient/{ingredient_id}/category", "/ingredient/{ingredient_id}/storage-type").permitAll()
                                .requestMatchers("/ingredient/{ingredient_id}/photo","/ingredient/search/name", "/ingredient/category/major-to-minor").permitAll()
                                .requestMatchers("/ingredient/majorCategory/list", "/ingredient/minorCategory/list", "/ingredient/minorCategory/", "/ingredient/minorCategory/search").permitAll()
                                // Fridge 관련 접근
                                .requestMatchers("/fridge/recent", "/fridge/near-expiry", "/fridge/far-expiry").permitAll()
                                .requestMatchers("/fridge/majorCategory/recent", "/fridge/majorCategory/near-expiry", "/fridge/majorCategory/far-expiry").permitAll()
                                .requestMatchers("/fridge/name/recent", "/fridge/name/near-expiry", "/fridge/name/far-expiry").permitAll()
                                // OCR 관련 접근
                                .requestMatchers("/OCR/receipt", "/OCR/ingredient/{ingredient_id}", "/OCR/{receipt_id}/purchase-date").permitAll()
                                // Town 관련 접근
                                .requestMatchers("/town/").permitAll()
                                // Recipe 관련 접근
                                .requestMatchers("/recipes/{recipeId}/favorite", "/recipes/{recipeName}", "recipes/recommend", "recipes/popular", "recipes/favorite").permitAll()
                                // Chat 관련 접근
                                .requestMatchers("/chat/","/chat/rooms", "/chat/rooms/{chatRoomId}/messages", "/chat/{chatRoomId}").permitAll()
                                // Alert 관련 접근
                                .requestMatchers("/Alert/fcm/token").permitAll()
                                // 기타 관련 접근
                                .requestMatchers("/example/**").permitAll()
                                .requestMatchers("/", "/api-docs/**", "/api-docs/swagger-config/*", "/swagger-ui/*", "/swagger-ui/**", "/v3/api-docs/**", "/image/upload", "/image/delete").permitAll()
                                .anyRequest().authenticated()
                )
//                .addFilterBefore(new JwtRequestFilter(jwtTokenProvider, principalDetailsService), UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*", "http://localhost:3000"));
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

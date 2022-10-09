package com.gasull.carlos.DiceGame.Security;

import com.gasull.carlos.DiceGame.entity.Player;
import com.gasull.carlos.DiceGame.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PlayerRepository playerRepository;

    public UserDetails loadUserByUsername (Long id) throws Exception {
        Player player = playerRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Id not found"));
        return PlayerPrincipal.build(player);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            Long id = Long.parseLong(username);
        try {
            return loadUserByUsername(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
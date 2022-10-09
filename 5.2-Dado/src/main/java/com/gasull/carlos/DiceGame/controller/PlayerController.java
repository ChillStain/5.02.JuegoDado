package com.gasull.carlos.DiceGame.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.gasull.carlos.DiceGame.Security.JwtInfo;
import com.gasull.carlos.DiceGame.Security.JwtProvider;
import com.gasull.carlos.DiceGame.Security.JwtTokenFilter;
import com.gasull.carlos.DiceGame.Security.PlayerPrincipal;
import com.gasull.carlos.DiceGame.entity.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gasull.carlos.DiceGame.entity.Player;
import com.gasull.carlos.DiceGame.service.PlayerService;

@RestController
public class PlayerController {

	@Autowired
	PlayerService playerService;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	    //get players
		@GetMapping("players/getAll")
		public ResponseEntity<?> getAll() {

			//playerService.updatePercentage();

			return (ResponseEntity.status(HttpStatus.OK)).body(playerService.getAllPlayers());
		}

		//add player
		@PostMapping("players/add")
		public ResponseEntity<?> savePlayer(@RequestBody Player player) {

			if (!playerService.playerExistsByName(player)) {

				player.setPassword(passwordEncoder.encode(player.getPassword()));
				Player newPlayer = new Player(player.getName(), player.getPassword());

				return ResponseEntity.status(HttpStatus.CREATED).body(playerService.addPlayer(newPlayer));
			} else {

				return (ResponseEntity.status(HttpStatus.BAD_REQUEST)).body("This player Already exists.");
			}
		}
		
		//update player
		@PutMapping("/players/{id}")
		public ResponseEntity<?> updatePlayer(@RequestBody Player player, @PathVariable(value = "id") Long id)
				throws Exception {

			Player playerFound = playerService.getPlayerById(id);

			if (player.getName() != null && !playerService.playerExistsByName(player)) {

				playerFound.setName(player.getName());

				return ResponseEntity.status(HttpStatus.OK).body(playerService.updatePlayer(playerFound));
			} else {

				return (ResponseEntity.status(HttpStatus.BAD_REQUEST)).body("This name already exists.");
			}

		}
		//Get player by Id
		@GetMapping("/players/{id}")
		public ResponseEntity<?> getPlayerById (@PathVariable (value="id") Long id) throws Exception {
			
			return ResponseEntity.status(HttpStatus.OK).body(playerService.getPlayerById(id));
		}

		@GetMapping("/players/ranking")
		public ResponseEntity<?> getAverageRanking() {

			//playerService.getAverageRanking();

			return ResponseEntity.status(HttpStatus.OK)
					.body("The average success rate of all players is " + playerService.getAverageRanking() + " %");
		}

		@GetMapping("/players/ranking/loser")
		public ResponseEntity<?> getWorstPlayer() {

			return ResponseEntity.status(HttpStatus.OK).body(playerService.getWorstPlayer(playerService.getAllPlayers()));
		}

		@GetMapping("/players/ranking/winner")
		public ResponseEntity<?> getBestPlayer() {

			return ResponseEntity.status(HttpStatus.OK).body(playerService.getBestPlayer(playerService.getAllPlayers()));
		}

		// delete player
		@DeleteMapping(path = "players/delete/{id}")
		public ResponseEntity<?> deletePlayer(@PathVariable("id") Long id) {
			boolean ok = this.playerService.deletePlayer(id);
			if (ok) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		}
	@PostMapping("/login")
	public ResponseEntity<JwtInfo> loginPlayer(@RequestBody Login loginPlayer){

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						loginPlayer.getIdPlayer(),
						loginPlayer.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		PlayerPrincipal userDetails = (PlayerPrincipal) authentication.getPrincipal();
		JwtInfo jwtInfo = new JwtInfo(jwt, userDetails.getUserId(), userDetails.getAuthorities());
		return new ResponseEntity<>(jwtInfo, HttpStatus.OK);
	}

		

}

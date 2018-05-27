package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.UUID;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter.StatBlock;
import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ActionData;
import actiondata.ErroredResponseData;
import actiondata.SuccessResponseData;
import message.Message;
import message.Response;

public class AllocateStatsAction extends RequestAction {

	private Integer stamina;
	private Integer luck;
	private Integer intuition;
	
	public AllocateStatsAction(final String authToken, final UUID messageID, 
							   final Integer stamina, final Integer luck, final Integer intuition) {
		super(Action.ALLOCATE_STATS, authToken, messageID);
		this.stamina = stamina;
		this.luck = luck;
		this.intuition = intuition;
	}

	@Override
	protected void doAction() {
		
		String auth = getCallingAuthToken();
		Game game = GameManager.getInstance().getGameForPlayer(auth);
		Message message;
		// Check to see if the player auth token was invalid / they are not a real player
		if (game != null) {
			GameState state = game.getGameState();
			
			PlayerCharacter character = state.getCharacter(auth);
			StatBlock stats = new StatBlock(this.stamina, this.luck, this.intuition);
			character.setStats(stats);
			state.syncGameBoardsPlayerList();

			if (Action.VERBOSE) {
				System.out.printf("Player %s allocated stats: [Stamina: %d, Luck: %d, Intuition: %d]\n",
								  character.getCharacterName(), 
								  character.getBaseStats().getStat(Stat.STAMINA),
								  character.getBaseStats().getStat(Stat.LUCK),
								  character.getBaseStats().getStat(Stat.INTUITION));
			}
			
			SuccessResponseData data = new SuccessResponseData(ActionData.ALLOCATE_STATS);			
			message = new Response(data, 0, this.getMessageID(), this.getCallingAuthToken());
		} else {
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.GAME_NOT_FOUND, this.getMessageID(), this.getCallingAuthToken());
		}
		// Send the response back to the calling session.
		try {
			Session session = SessionManager.getInstance().getSession(getCallingAuthToken());
			synchronized (session) {
				session.getBasicRemote().sendText(message.jsonify().toString());
			}
		} catch (IOException e) {
			// Error cannot be sent, so display in console
			e.printStackTrace();
		}
	}
	
	public static AllocateStatsAction fromMessage(final Message message) {
		return null;
	}

}

/*
 * Copyright (c) 2009-2010, Sergey Karakovskiy and Julian Togelius
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Mario AI nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package ch.idsia.agents.controllers;

import ch.idsia.agents.Agent;
import java. util. Random;
import java.lang.System.*;
import ch.idsia.benchmark.mario.engine.GeneralizerLevelScene;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.engine.sprites.Sprite;
import ch.idsia.benchmark.mario.environments.Environment;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Karakovskiy, sergey.karakovskiy@gmail.com
 * Date: Apr 8, 2009
 * Time: 4:03:46 AM
 */

public class OwnAgent3 extends BasicMarioAIAgent implements Agent
{
private boolean pressedJump = true;
private boolean pressedDown = true;
private boolean pressedShoot = true;
private boolean pressedLeft = true;
private boolean pressedRight = true;

int trueJumpCounter = 0;
int trueSpeedCounter = 0;

public OwnAgent3()
{
    super("OwnAgent3");
    reset();
}

public void reset()
{
    action = new boolean[Environment.numberOfKeys];
}

private boolean isCreature(int c)
{
    switch (c)
    {
        case Sprite.KIND_GOOMBA:
        case Sprite.KIND_RED_KOOPA:
        case Sprite.KIND_RED_KOOPA_WINGED:
        case Sprite.KIND_GREEN_KOOPA_WINGED:
        case Sprite.KIND_GREEN_KOOPA:
            return true;
    }
    return false;
}
public boolean[] getAction()
{
	
	 int r = marioEgoRow;
	 int c = marioEgoCol;
	 action = new boolean[Environment. numberOfKeys];
	 action[Mario. KEY_RIGHT] = true;
	 action[Mario.KEY_SPEED] = false;
	 Random R = new Random();
	 
//jumps over obstacles
	if(isObstacle(marioEgoRow, marioEgoCol + 1) ||
	getEnemiesCellValue(marioEgoRow, marioEgoCol +
	2) != Sprite.KIND_NONE
	|| getEnemiesCellValue(marioEgoRow, marioEgoCol
	+ 1) != Sprite.KIND_NONE){
	action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
	}
//3 1 stuck obstacles thing
	if(isObstacle(marioEgoRow, marioEgoCol + 1) &&
			isObstacle(marioEgoRow + 3, marioEgoCol + 1)) {
		action[Mario.KEY_RIGHT] = true;
		action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
//		action[Mario.KEY_DOWN] = !pressedDown;
		action[Mario.KEY_SPEED] = true;
		
	}
//if enemy is above and in front of you
	if(isCreature(enemies[r - 4][c + 4]) || isCreature(enemies[r - 3][c + 3]) 
		|| isCreature(enemies[r - 3][c + 4]) || isCreature(enemies[r - 2][c + 3])) {
		action[Mario.KEY_RIGHT] = false;
		action[Mario.KEY_LEFT] = false;
		action[Mario.KEY_DOWN] = true;
	}
//if enemy is in front of you
	if(isCreature(enemies[r][c + 2]) || isCreature(enemies[r][c + 1]) || isCreature(enemies[r][c + 3]) ||
			isCreature(enemies[r][c + 4])) {
			action[Mario.KEY_SPEED] = !pressedShoot;
			action[Mario.KEY_JUMP] = !pressedJump;
			action[Mario.KEY_DOWN] = !pressedDown;
//			pressedShoot = !pressedShoot;
		}
//if enemy is strictly above you and jumping down 
	if(isCreature(enemies[r - 1][c + 1]) || isCreature(enemies[r - 2][c + 2]) 
			|| isCreature(enemies[r - 1][c + 2])
			|| isCreature(enemies[r - 2][c + 3])
			|| isCreature(enemies[r - 3][c + 3])
			|| isCreature(enemies[r - 4][c + 4])) {
			action[Mario.KEY_DOWN] = !pressedDown;
			action[Mario.KEY_SPEED] = !pressedShoot;
		}
//if enemy is below you. 
	if(isCreature(enemies[r + 2][c + 2]) 
			|| isCreature(enemies[r + 1][c + 2])
			|| isCreature(enemies[r + 1][c + 1])
			|| isCreature(enemies[r + 3][c + 2])
			|| isCreature(enemies[r + 2][c + 3])
			|| isCreature(enemies[r + 3][c + 3])
			|| isCreature(enemies[r + 4][c + 4])) {
			action[Mario.KEY_JUMP] = !pressedJump;
			action[Mario.KEY_RIGHT] = true;
			action[Mario.KEY_SPEED] = !pressedShoot;
		}
	
//jumps over hole NEED
	if(isHole(marioEgoRow, marioEgoCol + 2) || isHole(marioEgoRow, marioEgoCol + 1)){
			action[Mario.KEY_SPEED] = true;
			action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
			}
//tries to jump over wall
//	if(isWall(marioEgoRow + 1, marioEgoCol + 9) || isWall(marioEgoRow + 1, marioEgoCol + 8)
//			){
//		action[Mario.KEY_LEFT] = true;
//		action[Mario.KEY_RIGHT] = false;
//		action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
//		action[Mario.KEY_SPEED] = false;
//		}
//	//tries to jump on block
//	if(isWallPiece(marioEgoRow - 7, marioEgoCol + 5) || isWallPiece(marioEgoRow - 7, marioEgoCol + 6)) {
//		action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
//		action[Mario.KEY_LEFT] = true; 
//	}
//	if(isWallPiece(marioEgoRow - 6, marioEgoCol + 7) || isWallPiece(marioEgoRow - 5, marioEgoCol + 7)) {
//		action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
//		action[Mario.KEY_LEFT] = true; 
//		action[Mario.KEY_SPEED] = false;
//	}
//	//tries to destroy block
//	if(isWallPiece(marioEgoRow - 3, marioEgoCol + 3) && isWallPiece(marioEgoRow - 2, marioEgoCol + 4)) {
//		action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
//		action[Mario.KEY_LEFT] = true;
//	}
	if(distancePassedCells == 98 || distancePassedCells == 99 || distancePassedCells == 100) {
		action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
		action[Mario.KEY_LEFT] = false; 
		action[Mario.KEY_RIGHT] = true;
	}
	if(distancePassedCells == 102 || distancePassedCells == 103) {
		action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
		action[Mario.KEY_RIGHT] = false; 
		action[Mario.KEY_LEFT] = false; 
	}
	if(distancePassedCells == 102 || distancePassedCells == 101 || distancePassedCells == 100) {
		action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
		action[Mario.KEY_LEFT] = false; 
		action[Mario.KEY_RIGHT] = true;
	}
	if(distancePassedCells > 130 && distancePassedCells < 140) {
		action[Mario.KEY_JUMP] = !pressedJump;
	}
	if(distancePassedCells == 224) {
		action[Mario.KEY_DOWN] = false;
	}
//stops on wall
//	if(isBlock(marioEgoRow + 1, marioEgoCol)){
////		action[Mario.KEY_LEFT] = true;
////		action[Mario.KEY_RIGHT] = true;
//		action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
//		action[Mario.KEY_SPEED] = false;
//		} 
//	if(isBlock(marioEgoRow - 2, marioEgoCol + 3) 
//			&& isBlock(marioEgoRow - 6, marioEgoCol + 6)){
//		action[Mario.KEY_LEFT] = true;
//		action[Mario.KEY_RIGHT] = true;
//		action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
//		action[Mario.KEY_SPEED] = false;
//		} 

	pressedShoot = action[Mario.KEY_SPEED];
	pressedRight = action[Mario.KEY_RIGHT];
	pressedLeft = action[Mario.KEY_LEFT];
	pressedDown = action[Mario.KEY_DOWN];
	pressedJump = action[Mario.KEY_JUMP];
	System.out.println(distancePassedPhys + "," + timeSpent );
	return action;
}

public boolean isObstacle(int r, int c){
	return getReceptiveFieldCellValue(r,
	c)==GeneralizerLevelScene.BRICK
	|| getReceptiveFieldCellValue(r,
	c)==GeneralizerLevelScene.BORDER_CANNOT_PASS_THROUGH
	|| getReceptiveFieldCellValue(r,
	c)==GeneralizerLevelScene.FLOWER_POT_OR_CANNON
	|| getReceptiveFieldCellValue(r,
	c)==GeneralizerLevelScene.LADDER;
}

public boolean isHole(int r, int c) {
	for (int i = r; i < r + 19; i++) {
      if (getReceptiveFieldCellValue(i, c) != 0) {
            return false;
        }
	}
    return true;
}
public boolean isBlock(int r, int c) {
		 if (getReceptiveFieldCellValue(r, c) == -24) {
            return true;
	}
    return false;
}
public boolean isWall(int r, int c) {
	for (int i = r; i > r - 8; i--) {
      if (getReceptiveFieldCellValue(i, c) != -60) {
            return false;
        }
	}
    return true;
}

public boolean isWallPiece(int r, int c) {
	return getReceptiveFieldCellValue(r,c) == -60;
}

}


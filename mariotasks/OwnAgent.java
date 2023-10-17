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

public class OwnAgent extends BasicMarioAIAgent implements Agent
{
private boolean pressedJump = true;
private boolean pressedDown = true;
private boolean pressedShoot = true;
private boolean pressedLeft = true;
private boolean pressedRight = true;

int trueJumpCounter = 0;
int trueSpeedCounter = 0;

public OwnAgent()
{
    super("OwnAgent");
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
//	 action[Mario.KEY_SPEED] = true;
	 Random R = new Random();
	 
//jumps over obstacles
	if(isObstacle(marioEgoRow, marioEgoCol + 1) ||
	getEnemiesCellValue(marioEgoRow, marioEgoCol +
	2) != Sprite.KIND_NONE
	|| getEnemiesCellValue(marioEgoRow, marioEgoCol
	+ 1) != Sprite.KIND_NONE){
	action[Mario.KEY_JUMP] = isMarioAbleToJump ||
	!isMarioOnGround;
	}
	//3 1 stuck obstacles thing
	if(isObstacle(marioEgoRow, marioEgoCol + 1) &&
			isObstacle(marioEgoRow - 3, marioEgoCol + 1) || isObstacle(marioEgoRow - 1, marioEgoCol + 1) ) {
		action[Mario.KEY_RIGHT] = isMarioAbleToJump ||!isMarioOnGround;
		action[Mario.KEY_JUMP] = R. nextBoolean();
	}
//	if(isCreature(enemies[r - 4][c + 4]) || isCreature(enemies[r - 3][c + 3]) 
//		|| isCreature(enemies[r - 3][c + 4]) || isCreature(enemies[r - 2][c + 3])) {
//		action[Mario.KEY_RIGHT] = false;
//		action[Mario.KEY_LEFT] = false;
//		action[Mario.KEY_JUMP] = !pressedJump;
//		action[Mario.KEY_DOWN] = !pressedDown;
////		pressedDown = !pressedDown;
////		action[Mario.KEY_SPEED] = pressedShoot;
//	}
	if(isCreature(enemies[r][c + 2]) || isCreature(enemies[r][c + 1]) || isCreature(enemies[r][c + 3]) ||
			isCreature(enemies[r][c + 4])) {
			action[Mario.KEY_SPEED] = !pressedShoot;
			action[Mario.KEY_JUMP] = !pressedJump;
			action[Mario.KEY_DOWN] = true;
//			pressedShoot = !pressedShoot;
		}
	if(isCreature(enemies[r - 1][c + 1]) || isCreature(enemies[r - 2][c + 2]) 
			|| isCreature(enemies[r - 1][c + 2])
			|| isCreature(enemies[r - 3][c + 2])
			|| isCreature(enemies[r - 2][c + 3])
			|| isCreature(enemies[r - 3][c + 3])
			|| isCreature(enemies[r - 4][c + 4])) {
			action[Mario.KEY_RIGHT] = false;
			action[Mario.KEY_LEFT] = !pressedLeft;
			action[Mario.KEY_JUMP] = false;
			action[Mario.KEY_DOWN] = false;
			action[Mario.KEY_SPEED] = !pressedShoot;
		}
	if(isCreature(enemies[r + 2][c + 2]) 
			|| isCreature(enemies[r + 1][c + 2])
			|| isCreature(enemies[r + 3][c + 2])
			|| isCreature(enemies[r + 2][c + 3])
			|| isCreature(enemies[r + 3][c + 3])
			|| isCreature(enemies[r + 4][c + 4])) {
			action[Mario.KEY_RIGHT] = !pressedLeft;
			action[Mario.KEY_LEFT] = !pressedLeft;
			action[Mario.KEY_JUMP] = true;
			action[Mario.KEY_DOWN] = false;
			action[Mario.KEY_SPEED] = !pressedShoot;
		}
	if(isHole(marioEgoRow, marioEgoCol + 2) || isHole(marioEgoRow, marioEgoCol + 1)){
			action[Mario.KEY_SPEED] = true;
			action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
			}
//	if(isDrop(marioEgoRow + 1, marioEgoCol + 1) && isDrop(marioEgoRow + 2, marioEgoCol + 1)){
//		action[Mario.KEY_SPEED] = true;
//		action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
//		}
	pressedShoot = action[Mario.KEY_SPEED];
	pressedRight = action[Mario.KEY_RIGHT];
	pressedLeft = action[Mario.KEY_LEFT];
	pressedDown = action[Mario.KEY_DOWN];
	pressedJump = action[Mario.KEY_JUMP];
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
public boolean isDrop(int r, int c) {
      if (getReceptiveFieldCellValue(r, c) != 0) {
            return false;
        }
    return true;
}
}
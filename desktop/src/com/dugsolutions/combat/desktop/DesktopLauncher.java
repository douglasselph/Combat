package com.dugsolutions.combat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dugsolutions.combat.TestBoxModel;
import com.dugsolutions.combat.TestShipModel;
import com.dugsolutions.combat.TestZombieModel;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new CombatTrainingGame(), config);
//		new LwjglApplication(new TestZombieModel(), config);
//		new LwjglApplication(new TestBoxModel(), config);
		new LwjglApplication(new TestShipModel(), config);
	}
}

package com.dugsolutions.combat

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera

class MyCamera {

    private lateinit var camera: PerspectiveCamera
    private var state = State.Stable
    private var dx: Int = 0
    private var dy: Int = 0

    companion object {
        private const val FIELD_OF_VIEW_Y = 75f
        private const val STEP_SZ = 15
    }

    private enum class State {
        Stable,
        Moving,
        Panning
    }

    fun create() {
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()

        camera = PerspectiveCamera(FIELD_OF_VIEW_Y, w, h)

        camera.position.set(0f, 100f, 100f)
        camera.lookAt(0f, 100f, 0f)

        camera.near = 0.1f
        camera.far = 300.0f
    }

    fun panStart(dx: Int, dy: Int) {
        this.dx = dx * STEP_SZ
        this.dy = dy * STEP_SZ
        state = State.Panning
    }

    fun panStop() {
        state = State.Stable
    }

    fun move(x: Int, y: Int) {
        dx = x
        dy = y
        state = State.Moving
    }

    fun update() {
        if (state == State.Panning) {
//            camera.translate(dx.toFloat(), dy.toFloat())
        } else if (state == State.Moving) {
            camera.position.set(dx.toFloat(), dy.toFloat(), camera.position.z)
            state = State.Stable
        }
        camera.update()
    }
}

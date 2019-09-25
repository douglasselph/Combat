package com.dugsolutions.combat

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader
import com.badlogic.gdx.graphics.g3d.utils.AnimationController
import com.badlogic.gdx.utils.UBJsonReader

class TestZombieModel : ApplicationAdapter() {

    companion object {
        private const val ZOMBIE = "zombie.g3db"
        private const val ANIMATION = "Armature|Armature|mixamo.com|Layer0"
    }

    private lateinit var camera: PerspectiveCamera
    private lateinit var modelBatch: ModelBatch
    private lateinit var model: Model
    private lateinit var modelInstance: ModelInstance
    private lateinit var environment: Environment
    private lateinit var controller: AnimationController

    override fun create() {
        camera = PerspectiveCamera(75f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        camera.position.set(0f, 100f, 200f)
        camera.lookAt(0f, 100f, 0f)

        camera.near = 0.1f
        camera.far = 300.0f

        modelBatch = ModelBatch()

        val jsonReader = UBJsonReader()

        val modelLoader = G3dModelLoader(jsonReader)
        model = modelLoader.loadModel(Gdx.files.getFileHandle(ZOMBIE, Files.FileType.Internal))
        modelInstance = ModelInstance(model, 0f, 100f, 0f)

        environment = Environment()
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f))

        controller = AnimationController(modelInstance)
        controller.setAnimation(ANIMATION, -1, object : AnimationController.AnimationListener {
            override fun onEnd(animation: AnimationController.AnimationDesc) {}

            override fun onLoop(animation: AnimationController.AnimationDesc) {
                Gdx.app.log("INFO", "Animation Ended")
            }
        })
    }

    override fun dispose() {
        modelBatch!!.dispose()
        model!!.dispose()
    }

    override fun render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        camera!!.update()
        controller!!.update(Gdx.graphics.deltaTime)

        modelBatch!!.begin(camera)
        modelBatch!!.render(modelInstance!!)
        modelBatch!!.end()
    }
}

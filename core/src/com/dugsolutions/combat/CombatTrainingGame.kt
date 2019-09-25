package com.dugsolutions.combat

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Files.FileType
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute.AmbientLight
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader
import com.badlogic.gdx.graphics.g3d.utils.AnimationController
import com.badlogic.gdx.math.collision.BoundingBox
import com.badlogic.gdx.utils.UBJsonReader

class CombatTrainingGame : ApplicationAdapter() {

    companion object {
        private val TAG = CombatTrainingGame::class.java.simpleName.toString()
        private const val MARIA = "maria_prop_j_j_ong.g3db"
        private const val HAS_ANIMATION = false
    }

    private lateinit var camera: PerspectiveCamera
    private lateinit var modelBatch: ModelBatch
    private lateinit var model: Model
    private lateinit var modelInstance: ModelInstance
    private lateinit var environment: Environment
    private var controller: AnimationController? = null

    override fun create() {
        val modelX = 0f
        val modelY = 0f
        val modelZ = 0f

        modelBatch = ModelBatch()

        val jsonReader = UBJsonReader()

        val modelLoader = G3dModelLoader(jsonReader)
        model = modelLoader.loadModel(Gdx.files.getFileHandle(MARIA, FileType.Internal))
        modelInstance = ModelInstance(model, modelX, modelY, modelZ)

        val boundingBox = BoundingBox()
        model.calculateBoundingBox(boundingBox)

        environment = Environment()
        environment.set(ColorAttribute(AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f))

        if (HAS_ANIMATION) {
            val animationController = AnimationController(modelInstance)
            controller = animationController
            animationController.setAnimation("mixamo.com", -1, object : AnimationController.AnimationListener {
                override fun onEnd(animation: AnimationController.AnimationDesc) {}

                override fun onLoop(animation: AnimationController.AnimationDesc) {
                    Gdx.app.log("INFO", "Animation Ended")
                }
            })
        }

//        modelInstance.transform.setToTranslation(modelX, modelY, modelZ)


        camera = PerspectiveCamera(75f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        val size = if (boundingBox.width > boundingBox.height) boundingBox.width else boundingBox.height
        camera.position.set(modelX, modelY, modelZ + size * 2)
        camera.lookAt(modelX, modelY, modelZ)

        camera.near = 1f
        camera.far = size * 3

        Gdx.app.log(TAG, "boundingBox=$boundingBox")
        Gdx.app.log(TAG, "Camera pos=${camera.position}")
    }

    override fun dispose() {
        modelBatch.dispose()
        model.dispose()
    }

    override fun render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        camera.update()
        controller?.update(Gdx.graphics.deltaTime)

        modelBatch.begin(camera)
        modelBatch.render(modelInstance)
        modelBatch.end()
    }
}

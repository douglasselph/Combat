package com.dugsolutions.combat

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader
import com.badlogic.gdx.graphics.g3d.utils.AnimationController
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.utils.UBJsonReader

class TestBoxModel : ApplicationAdapter() {

    private lateinit var camera: PerspectiveCamera
    private lateinit var modelBatch: ModelBatch
    private lateinit var model: Model
    private lateinit var modelInstance: ModelInstance
    private lateinit var environment: Environment

    override fun create() {
        camera = PerspectiveCamera(75f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        camera.position.set(50f, 150f, 100f)
        camera.lookAt(0f, 100f, 0f)

        camera.near = 0.1f
        camera.far = 300.0f

        modelBatch = ModelBatch()

        val modelBuilder = ModelBuilder()
        val attributes = VertexAttributes.Usage.Normal or VertexAttributes.Usage.Position
        model = modelBuilder.createBox(50f, 50f, 50f,
                    Material(ColorAttribute.createDiffuse(Color.BLUE)),
                    attributes.toLong())
        modelInstance = ModelInstance(model, 0f, 100f, 0f)

        environment = Environment()
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f))
    }

    override fun dispose() {
        modelBatch.dispose()
        model.dispose()
    }

    override fun render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        camera.update()

        modelBatch.begin(camera)
        modelBatch.render(modelInstance)
        modelBatch.end()
    }
}

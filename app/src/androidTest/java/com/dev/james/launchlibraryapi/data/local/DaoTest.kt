package com.dev.james.launchlibraryapi.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.dev.james.launchlibraryapi.models.OrbitRoom
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DaoTest {

    /**
     * Tests the app database
     * **/

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: LaunchDatabase
    private lateinit var dao : Dao


    @Before
    fun setUp(){

        //the database will  be created in
        // memory only the destroyed on teardown later

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LaunchDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.getDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertOrbitItem() = runBlockingTest {
        //insert orbit item: Will test the insert function
        val orbitItem = OrbitRoom(1 , "Trans lunar orbit")
        dao.addOrbit(orbitItem)

        //read orbit item : will test the reading function
        val insertedOrbitItem = dao.getOrbit(1)
        assertThat(insertedOrbitItem.description).isEqualTo("Trans lunar orbit")

    }
}
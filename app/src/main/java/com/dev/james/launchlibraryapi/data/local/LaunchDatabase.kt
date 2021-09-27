package com.dev.james.launchlibraryapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dev.james.launchlibraryapi.di.ApplicationScope
import com.dev.james.launchlibraryapi.models.OrbitRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(version = 1 , entities = [OrbitRoom::class])
abstract class LaunchDatabase : RoomDatabase() {

    abstract fun getDao() : Dao

    class Callback @Inject constructor(
        private val database : Provider<LaunchDatabase>,
        @ApplicationScope private val applicationScope : CoroutineScope
    ) : RoomDatabase.Callback(){

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            //get dao
            val dao = database.get().getDao()

            applicationScope.launch {

                dao.addOrbit(OrbitRoom(0 , "In astrodynamics or celestial mechanics, an elliptic orbit or elliptical orbit is a Kepler orbit with an eccentricity of less than 1; this includes the special case of a circular orbit, with eccentricity equal to 0. In a stricter sense, it is a Kepler" +
                        " orbit with the eccentricity greater than 0 and less " +
                        "than 1."))

                dao.addOrbit(OrbitRoom(1 , "A geostationary orbit, also referred to as a geosynchronous equatorial orbit, is a circular geosynchronous orbit 35,786 kilometres in altitude above Earth's equator and" +
                        " following the direction of Earth's rotation."))

                dao.addOrbit(OrbitRoom(2 , "A geosynchronous transfer orbit or geostationary transfer orbit (GTO) is a type of geocentric orbit. Satellites which are destined for geosynchronous (GSO) or geostationary orbit (GEO) are (almost) always put into a GTO as an intermediate step " +
                        "for reaching their final orbit. A GTO is " +
                        "highly elliptic."))

                dao.addOrbit(OrbitRoom(3 , "A geosynchronous orbit (GEO) is a prograde, low inclination orbit about Earth having a period of 23 hours 56 minutes 4 seconds. A spacecraft in geosynchronous orbit appears to remain above Earth at a constant" +
                        " longitude, although it may seem to wander north and south."))

                dao.addOrbit(OrbitRoom(4 , "A geosynchronous transfer orbit or geostationary transfer orbit (GTO) is a type of geocentric orbit. Satellites which are destined for geosynchronous (GSO) or geostationary orbit (GEO) are (almost) always put into a GTO as an intermediate " +
                        "step for reaching their final orbit." +
                        " A GTO is highly elliptic."))

                dao.addOrbit(OrbitRoom(5 , "A heliocentric orbit is an " +
                        "orbit around the barycenter of the Solar System, " +
                        "which is usually located within or very near the " +
                        "surface of the Sun. All planets, comets, and asteroids " +
                        "in the Solar System, and the Sun itself are in such" +
                        " orbits, as are many artificial probes" +
                        " and pieces of debris."))

                dao.addOrbit(
                    OrbitRoom(6 , "Same as heliocentric orbit difference being the Orbit " +
                        "has not been decided yet.")
                )

                dao.addOrbit(OrbitRoom(7 , "A high Earth orbit is a geocentric orbit with an altitude entirely above that of " +
                        "a geosynchronous orbit (35,786 kilometres, 22,236 mi)."))

                dao.addOrbit(OrbitRoom(8 , "A low Earth orbit is an" +
                        " Earth-centered orbit close to the planet, " +
                        "often specified as an orbital period of " +
                        "128 minutes or less and an eccentricity less" +
                        " than 0.25. Most of the artificial objects in outer" +
                        " space are in LEO, with an altitude " +
                        "never more than about one-third of the radius of the Earth."))

                dao.addOrbit(OrbitRoom(21 , "Orbit around an asteroid."))

                dao.addOrbit(OrbitRoom(17 ,
                        "A Sun-synchronous orbit (SSO, also called a helio-synchronous" +
                                " orbit) is a nearly polar orbit around a planet, in which the satellite passes over any given " +
                        "point of the planet's surface at the same local mean solar time."))

                dao.addOrbit(OrbitRoom(13 , "A Polar Orbit (PO) is an orbit in which a satellite passes above or nearly above Earth's North and South poles on each revolution." +
                        " It, therefore, has an inclination of (or very close to) 90 degrees to the equator."))

            }
        }

    }
}
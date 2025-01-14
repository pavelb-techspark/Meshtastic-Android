package com.geeksville.mesh.model.map

import org.osmdroid.tileprovider.tilesource.ITileSource
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.tileprovider.tilesource.TileSourcePolicy
import org.osmdroid.util.MapTileIndex


class CustomTileSource {
    companion object {

        // Map Server information: https://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer
        // Arcgis Information: https://www.arcgis.com/home/item.html?id=10df2279f9684e4a9f6a7f08febac2a9
        private val ESRI_IMAGERY = object : OnlineTileSourceBase(
            "ESRI World Overview", 0, 18, 256, ".jpg", arrayOf(
                "https://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/"
            ), "Esri, Maxar, Earthstar Geographics, and the GIS User Community",
            TileSourcePolicy(
                4,
                TileSourcePolicy.FLAG_NO_BULK
                        or TileSourcePolicy.FLAG_NO_PREVENTIVE
                        or TileSourcePolicy.FLAG_USER_AGENT_MEANINGFUL
                        or TileSourcePolicy.FLAG_USER_AGENT_NORMALIZED
            )
        ) {
            override fun getTileURLString(pMapTileIndex: Long): String {
                return baseUrl + (MapTileIndex.getZoom(pMapTileIndex)
                    .toString() + "/" + MapTileIndex.getY(pMapTileIndex)
                        + "/" + MapTileIndex.getX(pMapTileIndex)
                        + mImageFilenameEnding)
            }
        }

        //Transparent Background
        //https://earthlive.maptiles.arcgis.com/arcgis/rest/services/GOES/GOES31D/MapServer/tile/
        private val NOAA_RADAR = object : OnlineTileSourceBase(
            "NOAA GOES Radar",
            0,
            18,
            256,
            "",
            arrayOf(
                "https://earthlive.maptiles.arcgis.com/arcgis/rest/services/GOES/GOES31C/MapServer/tile/"
            ),
            "Dataset Citation: GOES-R Calibration Working Group and GOES-R Series Program, (2017): NOAA GOES-R Series Advanced Baseline Imager (ABI) Level 1b Radiances Band 13. NOAA National Centers for Environmental Information. doi:10.7289/V5BV7DSR",
            TileSourcePolicy(
                2,
                TileSourcePolicy.FLAG_NO_BULK
                        or TileSourcePolicy.FLAG_NO_PREVENTIVE
                        or TileSourcePolicy.FLAG_USER_AGENT_MEANINGFUL
                        or TileSourcePolicy.FLAG_USER_AGENT_NORMALIZED
            )
        ) {
            override fun getTileURLString(pMapTileIndex: Long): String {
                return baseUrl + (MapTileIndex.getZoom(pMapTileIndex)
                    .toString() + "/" + MapTileIndex.getY(pMapTileIndex)
                        + "/" + MapTileIndex.getX(pMapTileIndex)
                        + mImageFilenameEnding)
            }
        }
        private val USGS_HYDRO_CACHE = object : OnlineTileSourceBase(
            "USGS Hydro Cache",
            0,
            18,
            256,
            "",
            arrayOf(
                "https://basemap.nationalmap.gov/arcgis/rest/services/USGSHydroCached/MapServer/tile/"
            ),
            "USGS",
            TileSourcePolicy(
                2,
                TileSourcePolicy.FLAG_NO_PREVENTIVE
                        or TileSourcePolicy.FLAG_USER_AGENT_MEANINGFUL
                        or TileSourcePolicy.FLAG_USER_AGENT_NORMALIZED
            )
        ) {
            override fun getTileURLString(pMapTileIndex: Long): String {
                return baseUrl + (MapTileIndex.getZoom(pMapTileIndex)
                    .toString() + "/" + MapTileIndex.getY(pMapTileIndex)
                        + "/" + MapTileIndex.getX(pMapTileIndex)
                        + mImageFilenameEnding)
            }
        }
        private val USGS_SHADED_RELIEF = object : OnlineTileSourceBase(
            "USGS Shaded Relief Only",
            0,
            18,
            256,
            "",
            arrayOf(
                "https://basemap.nationalmap.gov/arcgis/rest/services/USGSShadedReliefOnly/MapServer/tile/"
            ),
            "USGS",
            TileSourcePolicy(
                2,
                TileSourcePolicy.FLAG_NO_PREVENTIVE
                        or TileSourcePolicy.FLAG_USER_AGENT_MEANINGFUL
                        or TileSourcePolicy.FLAG_USER_AGENT_NORMALIZED
            )
        ) {
            override fun getTileURLString(pMapTileIndex: Long): String {
                return baseUrl + (MapTileIndex.getZoom(pMapTileIndex)
                    .toString() + "/" + MapTileIndex.getY(pMapTileIndex)
                        + "/" + MapTileIndex.getX(pMapTileIndex)
                        + mImageFilenameEnding)
            }
        }

        /**
         * WMS TILE SERVER
         * More research is required to get this to function correctly with overlays
         */
        val NOAA_RADAR_WMS = NOAAWmsTileSource(
            "Recent Weather Radar",
            arrayOf("https://new.nowcoast.noaa.gov/arcgis/services/nowcoast/radar_meteo_imagery_nexrad_time/MapServer/WmsServer?"),
            "1",
            "1.3.0",
            "",
            "EPSG%3A3857",
            "",
            "image/png"
        )

        val NOAA_SATELLITE_RADAR_WMS = NOAAWmsTileSource(
            "Weather Satellite Imagery",
            arrayOf("https://new.nowcoast.noaa.gov/arcgis/services/nowcoast/sat_meteo_imagery_time/MapServer/WmsServer?"),
            "1,5,9,13,17,21,25",
            "1.3.0",
            "",
            "EPSG%3A3857",
            "",
            "image/png"
        )

        /**
         * ===============================================================================================
         */

        private val MAPNIK: OnlineTileSourceBase = TileSourceFactory.MAPNIK
        private val USGS_TOPO: OnlineTileSourceBase = TileSourceFactory.USGS_TOPO
        private val OPEN_TOPO: OnlineTileSourceBase = TileSourceFactory.OpenTopo
        private val USGS_SAT: OnlineTileSourceBase = TileSourceFactory.USGS_SAT
        private val SEAMAP: OnlineTileSourceBase = TileSourceFactory.OPEN_SEAMAP
        val DEFAULT_TILE_SOURCE: OnlineTileSourceBase = TileSourceFactory.DEFAULT_TILE_SOURCE

        /**
         * The order in this list must match that in the arrays.xml under map_styles
         */
        val mTileSources: List<ITileSource> =
            listOf(
                MAPNIK,
                USGS_TOPO,
                OPEN_TOPO,
                USGS_SAT,
                ESRI_IMAGERY,
            )


        fun getTileSource(aName: String): ITileSource {
            for (tileSource: ITileSource in mTileSources) {
                if (tileSource.name().equals(aName)) {
                    return tileSource;
                }
            }
            throw IllegalArgumentException("No such tile source: $aName")
        }
    }

}
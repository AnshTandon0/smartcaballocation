package com.androidants.smartcaballocation.data.model

import com.google.gson.annotations.SerializedName

data class GeoCodeResponse(
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("features")
    var features: ArrayList<Features> = arrayListOf(),
    @SerializedName("query")
    var query: Query? = Query()
)

data class Datasource(
    @SerializedName("sourcename")
    var sourcename: String? = null,
    @SerializedName("attribution")
    var attribution: String? = null,
    @SerializedName("license")
    var license: String? = null,
    @SerializedName("url")
    var url: String? = null
)

data class Timezone(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("offset_STD")
    var offsetSTD: String? = null,
    @SerializedName("offset_STD_seconds")
    var offsetSTDSeconds: Int? = null,
    @SerializedName("offset_DST")
    var offsetDST: String? = null,
    @SerializedName("offset_DST_seconds")
    var offsetDSTSeconds: Int? = null,
    @SerializedName("abbreviation_STD")
    var abbreviationSTD: String? = null,
    @SerializedName("abbreviation_DST")
    var abbreviationDST: String? = null
)

data class Rank(
    @SerializedName("importance") var importance: Double? = null,
    @SerializedName("popularity") var popularity: Double? = null
)

data class Properties(
    @SerializedName("datasource") var datasource: Datasource? = Datasource(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("country_code") var countryCode: String? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("postcode") var postcode: String? = null,
    @SerializedName("district") var district: String? = null,
    @SerializedName("suburb") var suburb: String? = null,
    @SerializedName("street") var street: String? = null,
    @SerializedName("housenumber") var housenumber: String? = null,
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("distance") var distance: Int? = null,
    @SerializedName("result_type") var resultType: String? = null,
    @SerializedName("formatted") var formatted: String? = null,
    @SerializedName("address_line1") var addressLine1: String? = null,
    @SerializedName("address_line2") var addressLine2: String? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("timezone") var timezone: Timezone? = Timezone(),
    @SerializedName("plus_code") var plusCode: String? = null,
    @SerializedName("rank") var rank: Rank? = Rank(),
    @SerializedName("place_id") var placeId: String? = null
)

data class Geometry(
    @SerializedName("type") var type: String? = null,
    @SerializedName("coordinates") var coordinates: ArrayList<Double> = arrayListOf()
)

data class Features(
    @SerializedName("type") var type: String? = null,
    @SerializedName("properties") var properties: Properties? = Properties(),
    @SerializedName("geometry") var geometry: Geometry? = Geometry(),
    @SerializedName("bbox") var bbox: ArrayList<Double> = arrayListOf()
)


data class Query(
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("plus_code") var plusCode: String? = null
)
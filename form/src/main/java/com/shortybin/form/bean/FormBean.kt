package com.shortybin.form.bean

data class FormBean(
    val description: String,
    val modules: List<Module>,
    val style: List<Any>,
    val title: String
)

data class Module(
    val display: Boolean,
    val editable: Boolean,
    val engine: Engine,
    val `field`: String,
    val full_module: Boolean,
    val id: Int,
    val label: String,
    val placehold: String,
    val regx_list: List<Regx>,
    val required: Boolean,
    val resource: List<Resource>,
    val style: List<Any>,
    val tips: String,
    val value: String
)

data class Engine(
    val config: Config,
    val name: String
)

data class Regx(
    val error_message: String,
    val expression: String,
    val id: Int
)

data class Resource(
    val api_setting: ApiSetting,
    val id: Int,
    val options: List<Option>,
    val title: String,
    val type: Int
)

data class Config(
    val length: Int
)

data class ApiSetting(
    val children_key: String,
    val label_key: String,
    val list_key: String,
    val uri: String,
    val value_key: String
)

data class Option(
    val event: Event?,
    val label: String,
    val value: String
)

data class Event(
    val event_type: String,
    val link_module: List<Int>
)
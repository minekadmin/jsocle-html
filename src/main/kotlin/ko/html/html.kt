package ko.html

import kotlin.properties.ReadWriteProperty

object attributeHandler : ReadWriteProperty<AbstractElement, String?> {
    override fun get(thisRef: AbstractElement, desc: PropertyMetadata): String? {
        if (thisRef.attributes.containsKey(desc.name)) {
            return thisRef.attributes[desc.name]
        }
        return null
    }

    override fun set(thisRef: AbstractElement, desc: PropertyMetadata, value: String?) {
        if (value != null) {
            thisRef.attributes.put(desc.name, value)
        } else {
            if (thisRef.attributes.containsKey(desc.name)) {
                thisRef.attributes.remove(desc.name)
            }
        }
    }
}

abstract class AbstractElement(val name: String) {
    val attributes: MutableMap<String, String> = hashMapOf()

    abstract fun render(builder: StringBuilder)

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder)
        return builder.toString()
    }
}

abstract class Element(name: String) : AbstractElement(name) {
    override fun render(builder: StringBuilder) {
        builder.append("<").append(name)
        for ((name, value) in attributes) {
            builder.append(" ").append(name).append("=\"").append(value).append("\"")
        }
        builder.append(">")
        builder.append("</").append(name).append(">")
    }
}

open class Html(lang: String? = null) : Element("html") {
    var lang by attributeHandler

    override fun render(builder: StringBuilder) {
        builder.append("<!DOCTYPE html>")
        super.render(builder)
    }

    {
        this.lang = lang
    }
}

class Body : Element("body")

package lab256.serialization

import kotlinx.serialization.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.descriptors.*
import lab256.shapes.Color
import lab256.shapes.Square

object SquareSerializer : KSerializer<Square> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(Square::class.java.name) {
        element<Color>("borderColor")
        element<Color>("fillColor")
        element<Double>("side")
    }
    override fun deserialize(decoder: Decoder): Square {
        return decoder.decodeStructure(descriptor) {
            var borderColor: Color? = null
            var fillColor: Color? = null
            var side: Double? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    -1 -> break
                    0 -> borderColor = decodeSerializableElement(descriptor, 0, serializer())
                    1 -> fillColor = decodeSerializableElement(descriptor, 1, serializer())
                    2 -> side = decodeDoubleElement(descriptor, 2)
                    else -> throw SerializationException("Unexpected index $index")
                }
            }

            Square(borderColor!!, fillColor!!, side!!)
        }
    }

    override fun serialize(encoder: Encoder, value: Square) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, serializer(), value.borderColor)
            encodeSerializableElement(descriptor, 1, serializer(), value.fillColor)
            encodeDoubleElement(descriptor, 2, value.side1)
        }
    }

}
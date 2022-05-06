package lab6.serialization

import kotlinx.serialization.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.descriptors.*
import lab6.shapes.Color
import lab6.shapes.Square

object SquareSerializer : KSerializer<Square> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(Square::class.java.name) {
        element<Color>("borderColor")
        element<Color>("fillColor")
        element<Double>("side")
    }
    override fun deserialize(decoder: Decoder): Square {
        return decoder.decodeStructure(descriptor) {
            var fillColor: Color? = null
            var borderColor: Color? = null
            var side: Double? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    -1 -> break
                    0 -> fillColor = decodeSerializableElement(descriptor, 0, serializer())
                    1 -> borderColor = decodeSerializableElement(descriptor, 1, serializer())
                    2 -> side = decodeDoubleElement(descriptor, 2)
                    else -> throw SerializationException("Unexpected index $index")
                }
            }

            Square(fillColor!!, borderColor!!, side!!)
        }
    }

    override fun serialize(encoder: Encoder, value: Square) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, serializer(), value.fillColor)
            encodeSerializableElement(descriptor, 1, serializer(), value.borderColor)
            encodeDoubleElement(descriptor, 2, value.side1)
        }
    }

}
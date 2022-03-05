package mkk.csb.sleepingcity.model.roles

class Granny() : Role() {
    override val attitude : Attitudes = Attitudes.good
    override val description : String = "The lovely old granny."
    override val className : String = "Granny"

    override fun toString(): String {
        return className
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Granny

        if (attitude != other.attitude) return false
        if (description != other.description) return false
        if (className != other.className) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + attitude.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + className.hashCode()
        return result
    }

}

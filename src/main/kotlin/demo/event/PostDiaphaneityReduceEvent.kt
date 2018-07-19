package demo.event

import demo.powers.DiaphaneityPower

class PostDiaphaneityReduceEvent(val diaphaneityPower: DiaphaneityPower) : Event(ID) {

    companion object {
        val ID = "Post Diaphaneity Reduce"
    }

    override fun execute() {}

}
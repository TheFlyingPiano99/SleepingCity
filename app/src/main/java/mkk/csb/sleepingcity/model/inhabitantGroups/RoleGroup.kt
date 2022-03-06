package mkk.csb.sleepingcity.model.inhabitantGroups

import mkk.csb.sleepingcity.model.Role

class RoleGroup(val role : Role) : InhabitantGroup() {
    var nextGroup : RoleGroup? = null

}
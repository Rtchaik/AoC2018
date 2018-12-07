package advent2018.day07

const val numWorkers = 5
const val stepDuration = 60

val input = listOf(
    """Step F must be finished before step P can begin.""",
    """Step R must be finished before step J can begin.""",
    """Step X must be finished before step H can begin.""",
    """Step L must be finished before step N can begin.""",
    """Step U must be finished before step Z can begin.""",
    """Step B must be finished before step C can begin.""",
    """Step S must be finished before step C can begin.""",
    """Step N must be finished before step Y can begin.""",
    """Step I must be finished before step J can begin.""",
    """Step H must be finished before step K can begin.""",
    """Step G must be finished before step Z can begin.""",
    """Step Q must be finished before step V can begin.""",
    """Step E must be finished before step P can begin.""",
    """Step P must be finished before step W can begin.""",
    """Step J must be finished before step D can begin.""",
    """Step V must be finished before step W can begin.""",
    """Step T must be finished before step D can begin.""",
    """Step Z must be finished before step A can begin.""",
    """Step K must be finished before step A can begin.""",
    """Step Y must be finished before step O can begin.""",
    """Step O must be finished before step W can begin.""",
    """Step C must be finished before step M can begin.""",
    """Step D must be finished before step A can begin.""",
    """Step W must be finished before step M can begin.""",
    """Step M must be finished before step A can begin.""",
    """Step C must be finished before step A can begin.""",
    """Step F must be finished before step Z can begin.""",
    """Step I must be finished before step A can begin.""",
    """Step W must be finished before step A can begin.""",
    """Step T must be finished before step C can begin.""",
    """Step S must be finished before step K can begin.""",
    """Step B must be finished before step J can begin.""",
    """Step O must be finished before step A can begin.""",
    """Step Q must be finished before step P can begin.""",
    """Step G must be finished before step M can begin.""",
    """Step R must be finished before step T can begin.""",
    """Step B must be finished before step G can begin.""",
    """Step J must be finished before step O can begin.""",
    """Step X must be finished before step E can begin.""",
    """Step X must be finished before step C can begin.""",
    """Step H must be finished before step Y can begin.""",
    """Step Y must be finished before step A can begin.""",
    """Step X must be finished before step W can begin.""",
    """Step H must be finished before step A can begin.""",
    """Step X must be finished before step A can begin.""",
    """Step I must be finished before step M can begin.""",
    """Step G must be finished before step J can begin.""",
    """Step N must be finished before step G can begin.""",
    """Step D must be finished before step M can begin.""",
    """Step L must be finished before step D can begin.""",
    """Step V must be finished before step T can begin.""",
    """Step I must be finished before step Y can begin.""",
    """Step S must be finished before step J can begin.""",
    """Step K must be finished before step Y can begin.""",
    """Step F must be finished before step R can begin.""",
    """Step U must be finished before step T can begin.""",
    """Step Z must be finished before step M can begin.""",
    """Step T must be finished before step Z can begin.""",
    """Step B must be finished before step I can begin.""",
    """Step E must be finished before step K can begin.""",
    """Step N must be finished before step J can begin.""",
    """Step X must be finished before step Q can begin.""",
    """Step F must be finished before step Y can begin.""",
    """Step H must be finished before step P can begin.""",
    """Step Z must be finished before step D can begin.""",
    """Step V must be finished before step O can begin.""",
    """Step E must be finished before step C can begin.""",
    """Step V must be finished before step C can begin.""",
    """Step P must be finished before step A can begin.""",
    """Step B must be finished before step N can begin.""",
    """Step S must be finished before step W can begin.""",
    """Step P must be finished before step D can begin.""",
    """Step L must be finished before step W can begin.""",
    """Step D must be finished before step W can begin.""",
    """Step K must be finished before step C can begin.""",
    """Step L must be finished before step M can begin.""",
    """Step R must be finished before step O can begin.""",
    """Step F must be finished before step L can begin.""",
    """Step R must be finished before step H can begin.""",
    """Step K must be finished before step O can begin.""",
    """Step T must be finished before step W can begin.""",
    """Step R must be finished before step K can begin.""",
    """Step C must be finished before step W can begin.""",
    """Step N must be finished before step T can begin.""",
    """Step R must be finished before step P can begin.""",
    """Step E must be finished before step M can begin.""",
    """Step G must be finished before step T can begin.""",
    """Step U must be finished before step K can begin.""",
    """Step Q must be finished before step D can begin.""",
    """Step U must be finished before step S can begin.""",
    """Step J must be finished before step V can begin.""",
    """Step P must be finished before step Y can begin.""",
    """Step X must be finished before step Z can begin.""",
    """Step U must be finished before step H can begin.""",
    """Step H must be finished before step M can begin.""",
    """Step I must be finished before step C can begin.""",
    """Step V must be finished before step M can begin.""",
    """Step N must be finished before step I can begin.""",
    """Step B must be finished before step K can begin.""",
    """Step R must be finished before step Q can begin.""",
    """Step O must be finished before step C can begin."""
)
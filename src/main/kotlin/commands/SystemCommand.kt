package commands

interface SystemCommand: BoundCommand {
    fun execute(logger: Logger)
}
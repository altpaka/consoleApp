package commands

class Exit: BoundCommand, AbstractDescription {
    override val title: String = "exit"
    override val help: String = "завершить программу (без сохранения в файл)"
}
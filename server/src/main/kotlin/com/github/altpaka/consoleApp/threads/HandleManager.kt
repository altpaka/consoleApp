package com.github.altpaka.consoleApp.threads

import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.commands.AuthentificationPersonCollection
import com.github.altpaka.consoleApp.sql.SQLAndMemoryCollection
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

class HandleManager(
    private var inputQueue: BlockingQueue<RequestInInputQueue>,
    private var outputQueue: BlockingQueue<RequestInOutputQueue>,
    private val collection: SQLAndMemoryCollection
) : Runnable {
    private val service: ExecutorService = Executors.newCachedThreadPool()
    private var isRunning = false

    override fun run() {
        this.isRunning = true
        try {
            while (this.isRunning) {
                service.execute(Handler(this.inputQueue, this.outputQueue, this.collection))
            }
        } finally {
            this.isRunning = false
        }
    }
}
/*



///

///


///

///

///

///

///


 */
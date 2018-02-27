package com.irmansyah.catalogmovie.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by irmansyah on 13/12/17.
 */

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();
}

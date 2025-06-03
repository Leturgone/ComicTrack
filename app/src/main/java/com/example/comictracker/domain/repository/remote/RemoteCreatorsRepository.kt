package com.example.comictracker.domain.repository.remote

import com.example.comictracker.domain.model.CreatorModel

interface RemoteCreatorsRepository {

    suspend fun getSeriesCreators(creatorsRoles: List<Pair<Int, String>>):Result<List<CreatorModel>>

    suspend fun  getComicCreators(creatorsRoles: List<Pair<Int, String>>):Result<List<CreatorModel>>

}
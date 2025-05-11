package com.example.comictracker.data.repository.remote

import android.util.Log
import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.api.dto.creatorsDTO.Results
import com.example.comictracker.domain.model.CreatorModel
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteCreatorsRepositoryImpl @Inject constructor(private val api: MarvelComicApi):RemoteCreatorsRepository {

    //Маппер
    private fun Results.toModel(role:String):CreatorModel{
        return CreatorModel(
            creatorId = this.id!!.toInt(),
            name = this.fullName!!,
            image = "${this.thumbnail?.path}.${this.thumbnail?.extension}",
            role = role
        )
    }

    override suspend fun getSeriesCreators(creatorsRoles: List<Pair<Int, String>>):
            List<CreatorModel>  {
        Log.i("Repository", "Start get sereies creators")
        val creators = coroutineScope {
            creatorsRoles.map { creator ->
                async {
                    api.getCreatorById(creator.first.toString())
                        .data!!.results[0].toModel(creator.second)
                }
            }.awaitAll() // Ждем завершения всех корутин и собираем результаты
        }
        Log.i("Repository", "creators got $creators")
        return creators
    }

    override suspend fun getComicCreators(creatorsRoles: List<Pair<Int, String>>): List<CreatorModel> {
        val creators = coroutineScope {
            creatorsRoles.map { creator ->
                async {
                    api.getCreatorById(creator.first.toString())
                        .data!!.results[0].toModel(creator.second)
                }
            }.awaitAll() // Ждем завершения всех корутин и собираем результаты
        }
        return creators
    }
}
package com.example.comictracker.data.repository.remote

import android.util.Log
import com.example.comictracker.data.api.MarvelComicApi
import com.example.comictracker.data.api.dto.creatorsDTO.Results
import com.example.comictracker.domain.model.CreatorModel
import com.example.comictracker.domain.repository.remote.RemoteCreatorsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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
            Result<List<CreatorModel>>  {

        return try {
            Log.i("getSeriesCreators", "Start get sereies creators")
            val creators = coroutineScope {
                creatorsRoles.map { creator ->
                    async {
                        api.getCreatorById(creator.first.toString())
                            .data!!.results[0].toModel(creator.second)
                    }
                }.awaitAll() // Ждем завершения всех корутин и собираем результаты
            }
            Log.i("getSeriesCreators", "creators got $creators")
            Result.success(creators)
        }catch (e:Exception){
            Log.e("getSeriesCreators", e.toString())
            Result.success(emptyList())
        }
    }

    override suspend fun getComicCreators(creatorsRoles: List<Pair<Int, String>>): Result<List<CreatorModel>> {
        return try {
            Log.i("getComicCreators", "Start get comic creators")
            val creators = coroutineScope {
                creatorsRoles.map { creator ->
                    async {
                        api.getCreatorById(creator.first.toString())
                            .data!!.results[0].toModel(creator.second)
                    }
                }.awaitAll() // Ждем завершения всех корутин и собираем результаты
            }
            Log.i("getComicCreators", "creators got $creators")
            Result.success(creators)
        }catch (e:Exception){
            Log.e("getComicCreators", e.toString())
            Result.success(emptyList())
        }
    }
}
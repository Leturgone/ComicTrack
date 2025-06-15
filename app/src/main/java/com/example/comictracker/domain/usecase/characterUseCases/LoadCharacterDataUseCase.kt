package com.example.comictracker.domain.usecase.characterUseCases

import com.example.comictracker.domain.repository.remote.RemoteCharacterRepository
import com.example.comictracker.domain.repository.remote.RemoteSeriesRepository
import kotlinx.coroutines.async

class LoadCharacterDataUseCase(
    private val characterRepository: RemoteCharacterRepository,
    private val seriesRepository: RemoteSeriesRepository
) {
    suspend operator fun invoke(characterId:Int){
        return async{
            characterRepository.getCharacterById(characterId)
        }
    }
}
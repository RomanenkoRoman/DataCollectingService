
$ ->
  $.get "/responses", (responses) ->
    $.each responses, (index, response) ->
      $("#responses").append $("<li>").text response.id





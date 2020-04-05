# Реализация задачи с отбора на стажировку JetBrains

## Условие задачи :

Представьте себя грузчиком работающим на товарной станции. В течении дня на станцию прибывают поезда и их надо немедленно разгружать.

Для каждого поезда известно:

- номер поезда,
- время прибытия,
- время, которое у вас займёт разгрузка,
- сумма, которую вам заплатят за разгрузку этого поезда.

Начав разгрузку поезда, вы должны ее закончить и не можете разгружать два поезда одновременно.

Необязательно браться за разгрузку всех поездов. На станции есть другие грузчики.

Ваша задача написать алгоритм, который по этим исходным данным максимизирует ваш заработок.

Очень желательны тесты. Было бы прекрасно также получить несколько слов о реализованном алгоритме, его источнике, достоинствах, ограничениях, вычислительной сложности и тп.

----

## 0-ое решение, которое использовано для стресс тестов

* Отсортируем поезда в порядке прибытия на станцию, при равенстве времени прибытия раньше должен идти поезд, разгрузка которого займет меньше времени
* Переберём все подмножества поездов, зафиксируем какое-то множество X, если ни один временной отрезок не перекрывается, то такая последовательность разгруки может быть , среди всех таких возьмем максимум

## 1-ое решение - O(n ^ 2)

* Так же отсортируем поезда

* Посчитаем dp[i] [j = {0, 1}] - максимальная полученная прибыль, рассматривая первые i поездов,    j = 0 - не разгружаем i - ый поезд, j = 1 - разгружаем

* Переходы из i - го состояния :

  ```
  for (int j = i + 1; j < n; j++)
      if (j < n) {
          if (checkTimeIntervals(trains[i], trains[j])) { // если временные отрезки не пересекаются
              dp[j][1] = Math.max(dp[j][1], dp[i][1] + trains[j].getPayment());
          }
          dp[j][0] = Math.max(dp[j][0], Math.max(dp[i][0], dp[i][1]));
      }
  if (i + 1 < n) {
      dp[i + 1][0] = Math.max(dp[i + 1][0], Math.max(dp[i][0], dp[i][1]));
  }
  ```

## 2-ое решение - O(nlogn)

* Опять же сортируем поезда

* Будем считать такую же динамику, но теперь заметим что для i-го поезда можно найти ближайщий справа поезд такой, что он и все после него могут быть разгружены после i-го, а для пересчёта i-го состояния надо взять максимум из всех, которые могут быть разгружены для него, поэтому будем считать нашу динамику используя дерево отрезков

* Найдем такой поезд бин поиском

* Для i-го поезда найдем поезд, который оптимальнее разгружать для него - просто максимум на префиксе, ```dp[i] [1]  = max(dp[i] [1], prefix_max + trains[i].getPayment())``` , где trains[i].getPayment() - выплата за разгруку i-го поезда, prefix_max - максимум на префиксе [0;i]

* Переход из i-го состояния - поставим в дереве отрезков на позицию i - dp[i] [1].

  

## Тесты

Для запуска тестов реализован ```StressTestRunner```. При запуске нужно указать аргументы в следующем формате :

```java StressTestRunner <checker_solution> <solution_to_stress> <test_сount> <max_count_of_trains>```

```checker_solution``` - реализация, которая будет высчитывать ответ на тест, это может быть: ```stupid``` - решение за O(n * 2 ^ n), ```square``` - решение за квадратичное время.

Для генерации тестов реализован  ```TestGenerator``` который принимает ```max_count_of_trains``` - максимальное количество поездов в генерируемых тестах. Возвращает строку с валидным тестом.
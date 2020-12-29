(ns christmas.chr19.day04-test
  (:require [christmas.chr19.day04 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment
  (def realfile "resources/chr19/day04real")

  (expect 594 (chr/day04 realfile))

  (defn basic-pw [number] (chr/password? chr/basic-pred number))
  (defn strict-pw [number] (chr/password? chr/strict-pred number))

  (expect true (boolean (basic-pw 111111)))
  (expect false (boolean (basic-pw 223450)))
  (expect false (boolean (basic-pw 123789)))

  (expect 364 (chr/day04b realfile))

  (expect true (boolean (strict-pw 112233)))
  (expect false (boolean (strict-pw 123444)))
  (expect true (boolean (strict-pw 111122))))
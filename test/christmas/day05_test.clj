(ns christmas.day05-test
  (:require [christmas.day05 :as chr]
  		  	[expectations :refer :all]
            [christmas.core :refer :all]
            [clojure.string :as str]))

(def realFile (slurp "resources/Input05real"))

(expect true (chr/match \a \A))
(expect false (chr/match \A \A))

(expect [\a] (chr/treat [] \a))
(expect [] (chr/treat [\a] \A))

(expect 10 (chr/exercise05 "dabAcCaCBAcCcaDA"))
(expect 6 (chr/exercise05 "aabAAB"))
(expect 0 (chr/exercise05 "aA"))
(expect 0 (chr/exercise05 "abBA"))
(expect 4 (chr/exercise05 "abAB"))

;(expect \J (first realFile))
;(expect \f (last realFile))
;(expect 9822 (chr/exercise05 realFile))

(expect (reverse "dbCBcD") (chr/reduceString "dabAcCaCBAcCcaDA" "[Aa]"))
(expect 4 (chr/exercise05b "dabAcCaCBAcCcaDA"))
;(expect 5726 (chr/exercise05b realFile))

(expect #"aA" (re-pattern "aA"))
(expect "[Aa]" (str "[" "A" (chr/cas "A") "]"))
(expect "dbcCCBcCcD" (str/replace "dabAcCaCBAcCcaDA" (re-pattern "[Aa]") ""))
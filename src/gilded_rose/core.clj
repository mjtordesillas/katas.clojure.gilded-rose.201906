(ns gilded-rose.core
  (:gen-class))

(def maximum-quality 50)
(def minimum-quality 0)

(defn- update-item-quality-by [item delta]
  (let [updated-quality (+ (:quality item) delta)]
    (cond
      (> updated-quality maximum-quality)
      (merge item {:quality maximum-quality})
      (< updated-quality minimum-quality)
      (merge item {:quality minimum-quality})
      :else
      (merge item {:quality updated-quality}))))

(defn- age-one-day [item]
  (merge item {:sell-in (dec (:sell-in item))}))

(defmulti update-item
          (fn [item] (:name item)))

(defmethod update-item "Backstage passes to a TAFKAL80ETC concert" [item]
  (let [item (age-one-day item)]
    (if (< (:sell-in item) 0)
      (merge item {:quality 0})
      (if (and (>= (:sell-in item) 5) (< (:sell-in item) 10))
        (update-item-quality-by item 2)
        (if (and (>= (:sell-in item) 0) (< (:sell-in item) 5))
          (update-item-quality-by item 3)
          (update-item-quality-by item 1))))))

(defmethod update-item "Aged Brie" [item]
  (let [item (age-one-day item)]
    (if (< (:sell-in item) 0)
      (update-item-quality-by item 2)
      (update-item-quality-by item 1))))

(defmethod update-item :default [item]
  (let [item (age-one-day item)]
    (if (< (:sell-in item) 0)
      (update-item-quality-by item -2)
      (update-item-quality-by item -1))))

(defmethod update-item "Sulfuras, Hand of Ragnaros" [item]
  (merge item {:quality 80}))

(defn update-quality [items]
  (map
    (fn [item] (update-item item))
    items))

(defn item [item-name, sell-in, quality]
  {:name item-name, :sell-in sell-in, :quality quality})

(defn update-current-inventory []
  (let [inventory
        [
         (item "+5 Dexterity Vest" 10 20)
         (item "Aged Brie" 2 0)
         (item "Elixir of the Mongoose" 5 7)
         (item "Sulfuras, Hand of Ragnaros" 0 80)
         (item "Backstage passes to a TAFKAL80ETC concert" 15 20)
         ]]
    (update-quality inventory)))
